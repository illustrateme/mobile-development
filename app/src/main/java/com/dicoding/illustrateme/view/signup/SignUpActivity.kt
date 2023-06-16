package com.dicoding.illustrateme.view.signup

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.dicoding.illustrateme.api.ApiConfig
import com.dicoding.illustrateme.databinding.ActivitySignupBinding
import com.dicoding.illustrateme.model.AuthResponse
import com.dicoding.illustrateme.model.SignUpInfo
import com.dicoding.illustrateme.view.signin.SignInActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.tvToSignIn.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signUpButton.setOnClickListener {
            val username = binding.edSignupUsername.text.toString().trim()
            val name = binding.edSignupName.text.toString().trim()
            val email = binding.edSignupEmail.text.toString().trim()
            val password = binding.edSignupPassword.text.toString().trim()
            val signUpInfo = SignUpInfo(username, name, email, password)

            when {
                username.isEmpty() -> {
                    binding.edSignupPassword.error = "Enter Username"
                }
                name.isEmpty() -> {
                    binding.edSignupPassword.error = "Enter Name"
                }
                email.isEmpty() -> {
                    binding.edSignupEmail.error = "Enter Email"
                }
                password.isEmpty() -> {
                    binding.edSignupPassword.error = "Enter Password"
                }
                password.length < 8 -> {
                    binding.edSignupPassword.error = "Minimum 8 Characters"
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.edSignupEmail.error = "Invalid Email Address"
                }
                else -> {
                    showLoading(true)
                    signUp(signUpInfo)
                }
            }
        }
    }

    private fun signUp(userData: SignUpInfo) {
        val client = ApiConfig.getApiService().signUp(userData)
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful){
                    startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                    finish()
                    Toast.makeText(this@SignUpActivity, "Account Created Successfully", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@SignUpActivity, "Username already exists", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}