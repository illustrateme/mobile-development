package com.dicoding.illustrateme.view.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.illustrateme.R

class UploadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }
}