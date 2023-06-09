package com.dicoding.illustrateme.view.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.illustrateme.R
import com.dicoding.illustrateme.adapter.UserPostAdapter
import com.dicoding.illustrateme.databinding.ActivityProfileBinding
import com.dicoding.illustrateme.preference.AuthPreference
import com.dicoding.illustrateme.utils.AuthViewModel
import com.dicoding.illustrateme.view.ViewModelFactory
import com.dicoding.illustrateme.view.main.MainActivity
import com.dicoding.illustrateme.view.signin.SignInActivity
import com.dicoding.illustrateme.view.upload.UploadActivity
import com.dicoding.illustrateme.view.user.UserViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

class ProfileActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityProfileBinding
    private lateinit var adapter: UserPostAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserPostAdapter()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViewModel()
    }

    private fun setupViewModel() {
        authViewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthPreference.getInstance(dataStore))
        )[AuthViewModel::class.java]

        userViewModel = UserViewModel()

        authViewModel.getUser().observe(this){user ->
            binding.apply {
                tvName.text = user.user.name
                tvEmail.text = user.user.email
            }

            val username = user.user.username
            userViewModel.setUser(user.token, username)
            showLoading(true)
        }

        userViewModel.getUser().observe(this){
            if (it != null) {
                binding.apply {
                    rvPost.layoutManager = LinearLayoutManager(this@ProfileActivity)
                    rvPost.setHasFixedSize(true)
                    rvPost.adapter = adapter
                    adapter.setList(it.posts)
                }
            }
            showLoading(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.upload -> {
                startActivity(Intent(this, UploadActivity::class.java))
            }
            R.id.signout -> {
                authViewModel.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}