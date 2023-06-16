package com.dicoding.illustrateme.view.signin

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.illustrateme.api.ApiConfig
import com.dicoding.illustrateme.databinding.ActivitySigninBinding
import com.dicoding.illustrateme.model.AuthResponse
import com.dicoding.illustrateme.model.SignInInfo
import com.dicoding.illustrateme.preference.AuthPreference
import com.dicoding.illustrateme.utils.AuthViewModel
import com.dicoding.illustrateme.view.ViewModelFactory
import com.dicoding.illustrateme.view.main.MainActivity
import com.dicoding.illustrateme.view.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "signin")
class SignInActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
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

    private fun setupViewModel() {
        authViewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthPreference.getInstance(dataStore))
        )[AuthViewModel::class.java]
    }

    private fun setupAction() {
        binding.tvToSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signInButton.setOnClickListener {
            val username = binding.edSigninUsername.text.toString().trim()
            val password = binding.edSigninPassword.text.toString().trim()
            val signInInfo = SignInInfo (username, password)

            when {
                username.isEmpty() -> {
                    binding.edSigninUsername.error = "Enter Username"
                }
                password.isEmpty() -> {
                    binding.edSigninPassword.error = "Enter Password"
                }
                password.length < 8 -> {
                    binding.edSigninPassword.error = "Minimum 8 Characters"
                }
                else -> {
                    showLoading(true)
                    signIn(signInInfo)
                }
            }
        }
    }

    private fun signIn(userData: SignInInfo){
        val client = ApiConfig.getApiService().signIn(userData)
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                showLoading(false)
                val resp = response.body()
                val token = resp?.data?.token
                val name = resp?.data?.user?.name
                val email = resp?.data?.user?.email
                val username = resp?.data?.user?.username
                if(response.isSuccessful){
                    if (token != null) {
                        if (name != null) {
                            if (email != null) {
                                if (username != null) {
                                    authViewModel.signIn(token, name, email, username)
                                }
                            }
                        }
                    }
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    finish()
                    Toast.makeText(this@SignInActivity, "Login Successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@SignInActivity, "Username and Password not matched", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@SignInActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    
}