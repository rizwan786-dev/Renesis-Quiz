package com.renesistech.jokeapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.renesistech.jokeapplication.databinding.RegisterFragmentDataBinding


class RegisterFragment : Fragment() {
    var binding: RegisterFragmentDataBinding? = null
    var firebaseFirestore: FirebaseFirestore? = null
    var ref: DocumentReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentDataBinding.inflate(inflater, container, false)
                .apply {
                    lifecycleOwner = this@RegisterFragment
                }
        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore?.collection("client")?.document();
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnReg?.setOnClickListener {
            if (binding?.regName?.text.toString() == "") {
                Toast.makeText(requireContext(), "Please type a username", Toast.LENGTH_SHORT).show()
            } else if (binding?.regEmail?.text.toString() == "") {
                Toast.makeText(requireContext(), "Please type an email id", Toast.LENGTH_SHORT).show()
            } else if (binding?.regPassword?.text.toString() == "") {
                Toast.makeText(requireContext(), "Please type a password", Toast.LENGTH_SHORT).show()
            } else {
                ref!!.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        Toast.makeText(requireContext(), "Sorry,this user exists", Toast.LENGTH_SHORT).show()
                    } else {
                        val reg_entry: MutableMap<String, Any> = HashMap()
                        reg_entry["Name"] = binding?.regName?.text.toString()
                        reg_entry["Email"] = binding?.regEmail?.text.toString()
                        reg_entry["Password"] = binding?.regPassword?.text.toString()

                        //   String myId = ref.getId();
                        firebaseFirestore!!.collection("client")
                                .add(reg_entry)
                                .addOnSuccessListener { Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                                }
                                .addOnFailureListener { e -> e.message?.let { it1 -> Log.d("Error", it1) } }
                    }
                }
            }
        }
    }

}