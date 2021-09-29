package com.renesistech.jokeapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.renesistech.jokeapplication.databinding.NewJokeFragmentDataBinding


class NewJokeFragment : Fragment() {
    var binding: NewJokeFragmentDataBinding? = null

    var activ: MainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = NewJokeFragmentDataBinding.inflate(inflater, container, false)
                .apply {
                    lifecycleOwner = this@NewJokeFragment
                }

        binding?.btnSave?.setOnClickListener {
            binding?.etNewJoke?.text?.toString()?.let { it1 -> activ.SaveToDb(it1)
                Toast.makeText(activity, "Joke Added Successfully", Toast.LENGTH_SHORT).show()
            }
        }


        binding?.btnShow?.setOnClickListener {
            val home =
                    Intent(requireContext(), MainActivity::class.java)
            //home.putExtra("NewJoke", binding?.etNewJoke?.text?.toString())
            startActivity(home)
        }

        return binding?.root
    }
}