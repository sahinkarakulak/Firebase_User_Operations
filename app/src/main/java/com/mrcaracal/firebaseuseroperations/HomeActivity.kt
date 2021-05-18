package com.mrcaracal.firebaseuseroperations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mrcaracal.firebaseuseroperations.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var documentReference: DocumentReference
    private lateinit var datas: HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        documentReference = firestore.collection("Users").document()

        datas = HashMap()
        datas.put("name", "Caracal")
        datas.put("country", "Turkey")
    }

    fun exit(view: View) {
        auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun save(view: View) {
        documentReference.set(datas)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Unsuccessful", Toast.LENGTH_SHORT).show()
            }
    }

    fun getData(view: View) {
        documentReference.get()
            .addOnCompleteListener{ mrcaracal ->
                if (mrcaracal.isSuccessful){
                    var documentSnapshot = mrcaracal.getResult()
                    if (documentSnapshot!!.exists()){
                        var data_name = documentSnapshot.get("name")
                        var data_country = documentSnapshot.get("country")
                        Toast.makeText(applicationContext, "Name: "+ data_name, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Unsuccessful", Toast.LENGTH_SHORT).show()
            }
    }

    fun saveCol(view: View) {}
    fun getDataCol(view: View) {}
}