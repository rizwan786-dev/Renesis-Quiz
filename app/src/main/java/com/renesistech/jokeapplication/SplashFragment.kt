package com.renesistech.jokeapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_splash, container, false)

        val navController = findNavController()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            navController.navigate(action)
//            activity?.finish()
        }, 3000)

        return view
    }

}