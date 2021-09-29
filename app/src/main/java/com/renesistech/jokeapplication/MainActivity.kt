package com.renesistech.jokeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore
    var newJoke: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //newJoke = intent.getStringExtra("NewJoke")
        //SaveToDb(newJoke.toString())
        GetJokes()
        //792154514524-ptvjf5irg9pp3goam7c1h5milnk1mlda.apps.googleusercontent.com
    }

    fun SaveToDb(jokename: String) {
        val user = hashMapOf(
                "joke" to jokename
        )

        db.collection("Jokes").add(user)
                .addOnSuccessListener {
                    Log.v("Joke","Added")
                    //Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Log.v("Joke","Not Added")
                    //Toast.makeText(this, "Not Successful", Toast.LENGTH_SHORT).show()
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