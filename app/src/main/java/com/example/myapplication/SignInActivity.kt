package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.username.text.toString().trim()
            val password = binding.passET.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                showLoadingIndicator()

                // Check if the email exists in Firestore
                firestore.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener { task ->
                        hideLoadingIndicator()
                        if (task.isSuccessful) {
                            val documents = task.result?.documents
                            if (documents != null && documents.isNotEmpty()) {
                                signInWithEmail(email, password)
                            } else {
                                showToast("Invalid email or password")
                            }
                        } else {
                            showToast("Sign-in failed. Please try again.")
                            Log.e("SignInActivity", "Firestore check failed", task.exception)
                        }
                    }
            } else {
                showToast("Empty fields are not allowed")
            }
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startMainActivity()
                } else {
                    showToast("Sign-in failed. ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: Finish the SignInActivity after starting MainActivity
    }

    private fun showLoadingIndicator() {
        // Implement logic to show loading indicator
    }

    private fun hideLoadingIndicator() {
        // Implement logic to hide loading indicator
    }
}
