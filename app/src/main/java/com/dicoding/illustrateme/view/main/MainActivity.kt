package com.dicoding.illustrateme.view.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.illustrateme.R
import com.dicoding.illustrateme.adapter.PostAdapter
import com.dicoding.illustrateme.databinding.ActivityMainBinding
import com.dicoding.illustrateme.model.PostsItem
import com.dicoding.illustrateme.preference.AuthPreference
import com.dicoding.illustrateme.utils.AuthViewModel
import com.dicoding.illustrateme.view.ViewModelFactory
import com.dicoding.illustrateme.view.profile.ProfileActivity
import com.dicoding.illustrateme.view.signin.SignInActivity
import com.dicoding.illustrateme.view.user.UserActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "main")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PostAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PostAdapter()

        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile -> {
                Intent(this, ProfileActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        mainViewModel = MainViewModel()

        authViewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthPreference.getInstance(dataStore))
        )[AuthViewModel::class.java]

        authViewModel.getUser().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
            else{
                mainViewModel.setPost(user.token)
            }
        }

        binding.apply {
            rvPost.layoutManager = LinearLayoutManager(this@MainActivity)
            rvPost.setHasFixedSize(true)
            rvPost.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : PostAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PostsItem) {
                Intent(this@MainActivity, UserActivity::class.java).also {
                    it.putExtra(UserActivity.EXTRA_USERNAME, data.author.username)
                    startActivity(it)
                }
            }
        })

        mainViewModel.getPosts().observe(this){
            if (it != null){
                adapter.setList(it)
            }
        }

    }


}