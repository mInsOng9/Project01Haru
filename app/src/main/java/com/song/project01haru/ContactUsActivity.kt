package com.song.project01haru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.song.project01haru.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {

    lateinit var binding:ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbar: androidx.appcompat.widget.Toolbar =binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}