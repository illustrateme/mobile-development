package com.dicoding.illustrateme.view.user

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.illustrateme.adapter.UserPostAdapter
import com.dicoding.illustrateme.databinding.ActivityProfileBinding
import com.dicoding.illustrateme.preference.AuthPreference
import com.dicoding.illustrateme.utils.AuthViewModel
import com.dicoding.illustrateme.view.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var adapter: UserPostAdapter
    private lateinit var authViewModel: AuthViewModel
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

        val username = intent.getStringExtra(EXTRA_USERNAME)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        authViewModel.getUser().observe(this) { user ->
            if (username != null) {
                userViewModel.setUser(user.token, username)
                showLoading(true)
            }
        }

        userViewModel.getUser().observe(this){
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvEmail.text = it.email
                    rvPost.layoutManager = LinearLayoutManager(this@UserActivity)
                    rvPost.setHasFixedSize(true)
                    rvPost.adapter = adapter
                    adapter.setList(it.posts)
                }
            }
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}