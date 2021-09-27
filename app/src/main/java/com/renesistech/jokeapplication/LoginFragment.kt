package com.renesistech.jokeapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore


class LoginFragment : Fragment(), View.OnClickListener {
    var login: Button? = null
    var register: Button? = null
    var pwd: EditText? = null
    var email: TextView? = null
    var progress: ProgressBar? = null

    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        email=view.findViewById(R.id.user_email);
        pwd=view.findViewById(R.id.user_Password);
        progress=view.findViewById(R.id.prg_bar);
        login = view.findViewById(R.id.btnLogin);
        register = view.findViewById(R.id.create_signup);

        db= FirebaseFirestore.getInstance();
        login?.setOnClickListener(this);
        register?.setOnClickListener(this);
        return view
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {
                if (email!!.text.toString() == "") {
                    Toast.makeText(
                        requireContext(),
                        "Please enter valid email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (pwd!!.text.toString() == "") {
                    Toast.makeText(
                        requireContext(),
                        "Please enter valid password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                db!!.collection("client")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (doc in task.result) {
                                val a = doc.getString("Email")
                                val b = doc.getString("Password")
                                val a1 =
                                    email!!.text.toString().trim { it <= ' ' }
                                val b1 =
                                    pwd!!.text.toString().trim { it <= ' ' }
                                if (a.equals(a1, ignoreCase = true) and b.equals(
                                        b1,
                                        ignoreCase = true
                                    )
                                ) {
                                    val home =
                                        Intent(requireContext(), MainActivity::class.java)
                                    startActivity(home)
                                    Toast.makeText(
                                        requireContext(),
                                        "Logged In",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    break
                                } else Toast.makeText(
                                    requireContext(),
                                    "Cannot login,incorrect Email and Password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            }
            R.id.create_signup -> {
                val register_view = Intent(requireContext(), RegisterFragment::class.java)
                startActivity(register_view)
            }
        }
    }
}