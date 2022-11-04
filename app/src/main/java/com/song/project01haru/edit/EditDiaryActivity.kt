package com.song.project01haru.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.song.project01haru.databinding.ActivityEditDiaryBinding

class EditDiaryActivity : AppCompatActivity() {

    lateinit var binding:ActivityEditDiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }
}