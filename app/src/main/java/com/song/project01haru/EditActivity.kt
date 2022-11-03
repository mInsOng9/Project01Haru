package com.song.project01haru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.song.project01haru.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    lateinit var binding:ActivityEditBinding
    var expInTitle= arrayOf("Income","Expenses")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar
        var toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        //back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if(intent.getStringExtra("frag").toString().equals("expinc")) expInTitle=arrayOf("Income","Expenses")
        if(intent.getStringExtra("frag").toString().equals("todo")) expInTitle=arrayOf("Todo","")
        if(intent.getStringExtra("frag").toString().equals("skd"))expInTitle=arrayOf("Schedule","Event")

        var adapter= EditAdapter(this,intent.getStringExtra("frag").toString())


        binding.pager.setAdapter(adapter)

        var tabLayoutMediator:TabLayoutMediator=
            TabLayoutMediator(binding.tab,binding.pager,{tab,position-> tab.text=expInTitle[position] })

        tabLayoutMediator.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}