package com.renesistech.jokeapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.renesistech.jokeapplication.databinding.LoginFragmentDataBinding


class LoginFragment : Fragment(), View.OnClickListener {
    var binding: LoginFragmentDataBinding? = null

    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentDataBinding.inflate(inflater, container, false)
                .apply {
                    lifecycleOwner = this@LoginFragment
                }

        db= FirebaseFirestore.getInstance();
        binding?.btnLogin?.setOnClickListener(this)
        binding?.createSignup?.setOnClickListener(this)
        return binding?.root
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {
                if (binding?.userEmail?.text.toString() == "") {
                    Toast.makeText(
                            requireContext(),
                            "Please enter valid email",
                            Toast.LENGTH_SHORT
                    ).show()
                } else if (binding?.userPassword?.text.toString() == "") {
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
                                            binding?.userEmail?.text.toString().trim { it <= ' ' }
                                    val b1 =
                                            binding?.userPassword?.text.toString().trim { it <= ' ' }
                                    if (a.equals(a1, ignoreCase = true) and b.equals(
                                                    b1,
                                                    ignoreCase = true
                                            )
                                    ) {
                                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNewJokeFragment())
                                        /*val home =
                                            Intent(requireContext(), MainActivity::class.java)
                                        startActivity(home)*/
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
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                /*val register_view = Intent(requireContext(), RegisterFragment::class.java)
                startActivity(register_view)*/
            }
        }
    }
}