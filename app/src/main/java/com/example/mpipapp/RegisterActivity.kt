package com.example.mpipapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.example.mpipapp.databinding.ActivityLoginBinding
import com.example.mpipapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.setOnClickListener{
            it.hideKeyboard()
            binding.username.clearFocus()
            binding.password.clearFocus()
        }
        auth = FirebaseAuth.getInstance()
        binding.registerButton.setOnClickListener {
            val email = binding.username.text.toString()
            val password = binding.username.text.toString()

            if (TextUtils.isEmpty(binding.username.text.toString())) {
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (TextUtils.isEmpty(binding.password.text.toString())) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
               auth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this) { task ->
                      if (task.isSuccessful) {
                          val intent = Intent(this, NavigationActivity::class.java)
                          intent.putExtra("username", email)
                          startActivity(intent)
                          Toast.makeText(
                              baseContext,
                              "User successfully registered",
                              Toast.LENGTH_SHORT,
                          ).show()

                          // Sign in success, update UI with the signed-in user's information
                          val user = auth.currentUser
                      } else {
                          // If sign in fails, display a message to the user.
                          Toast.makeText(
                              baseContext,
                              "Authentication failed.",
                              Toast.LENGTH_SHORT,
                          ).show()
                      }
                     finish()
                  }
        }
    }
    fun View.hideKeyboard() = this.let { view ->
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.apply { hideSoftInputFromWindow(view.windowToken, 0) }
    }
}