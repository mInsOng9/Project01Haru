package com.song.project01haru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.song.project01haru.databinding.ActivityProfileBinding
import com.song.project01haru.main.MainActivity

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvOk.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.tvCancel.setOnClickListener { finish() }
    }
}