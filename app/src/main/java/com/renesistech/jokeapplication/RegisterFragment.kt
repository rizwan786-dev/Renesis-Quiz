package com.renesistech.jokeapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class RegisterFragment : Fragment() {
    var reg_registration: Button? = null
    var reg_name: EditText? = null
    var reg_email: EditText? = null
    var reg_password: EditText? = null
    var reg_conf_pwd: EditText? = null
    var signin: TextView? = null
    var firebaseFirestore: FirebaseFirestore? = null
    var ref: DocumentReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        reg_registration=view.findViewById(R.id.btn_reg);
        reg_name=view.findViewById(R.id.reg_name);
        reg_email=view.findViewById(R.id.reg_email);
        reg_password=view.findViewById(R.id.reg_password);
        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore?.collection("client")?.document();
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reg_registration!!.setOnClickListener {
            if (reg_name!!.text.toString() == "") {
                Toast.makeText(requireContext(), "Please type a username", Toast.LENGTH_SHORT).show()
            } else if (reg_email!!.text.toString() == "") {
                Toast.makeText(requireContext(), "Please type an email id", Toast.LENGTH_SHORT).show()
            } else if (reg_password!!.text.toString() == "") {
                Toast.makeText(requireContext(), "Please type a password", Toast.LENGTH_SHORT).show()
            } else {
                ref!!.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        Toast.makeText(requireContext(), "Sorry,this user exists", Toast.LENGTH_SHORT).show()
                    } else {
                        val reg_entry: MutableMap<String, Any> = HashMap()
                        reg_entry["Name"] = reg_name!!.text.toString()
                        reg_entry["Email"] = reg_email!!.text.toString()
                        reg_entry["Password"] = reg_password!!.text.toString()

                        //   String myId = ref.getId();
                        firebaseFirestore!!.collection("client")
                                .add(reg_entry)
                                .addOnSuccessListener { Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show() }
                                .addOnFailureListener { e -> e.message?.let { it1 -> Log.d("Error", it1) } }
                    }
                }
            }
        }
    }

}