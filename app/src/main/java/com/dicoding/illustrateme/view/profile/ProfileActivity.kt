package com.dicoding.illustrateme.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.dicoding.illustrateme.R
import com.dicoding.illustrateme.view.signin.SignInActivity
import com.dicoding.illustrateme.view.upload.UploadActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}