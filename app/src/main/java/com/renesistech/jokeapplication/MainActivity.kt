package com.renesistech.jokeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SaveToDb("Hi how are you...")
        GetJokes()
        //792154514524-ptvjf5irg9pp3goam7c1h5milnk1mlda.apps.googleusercontent.com
    }

    fun SaveToDb(jokename: String) {
        val user = hashMapOf(
            "joke" to jokename
        )

        db.collection("Jokes").add(user)
            .addOnSuccessListener {

                Toast.makeText(application, "Saved Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(application, "Not Successful", Toast.LENGTH_SHORT).show()
            }
    }

    fun GetJokes() {

        db.collection("Jokes").addSnapshotListener { snapshot, e ->

            val getjokes = ArrayList<JokesModel?>()

            for (document in snapshot!!) {

                getjokes.add(
                    JokesModel(
                        document.getString("joke")
                    )
                )
                val ty = Adapter(getjokes, applicationContext)
                recy.adapter = ty
                ty.notifyDataSetChanged()

            }
        }

    }
}