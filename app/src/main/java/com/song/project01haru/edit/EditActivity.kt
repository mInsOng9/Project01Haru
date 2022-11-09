package com.song.project01haru.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.song.project01haru.R
import com.song.project01haru.databinding.ActivityEditBinding
import java.text.SimpleDateFormat

//import com.song.project01haru.main.todo.TodoItem
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.scalars.ScalarsConverterFactory

class EditActivity : AppCompatActivity() {

    lateinit var binding:ActivityEditBinding
    var tabTitle :MutableList<String> = mutableListOf()


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

        //Tab
        if(intent.getStringExtra("frag").toString().equals("expinc")) tabTitle=
            mutableListOf("Income","Expenses")

        if(intent.getStringExtra("frag").toString().equals("skd"))tabTitle=
            mutableListOf("Schedule","Event")

        var adapter= EditAdapter(this,intent.getStringExtra("frag").toString())

        //tabLayoutMediator
        var tabLayoutMediator:TabLayoutMediator

        binding.pager.setAdapter(adapter)
        if(intent.getStringExtra("frag").toString().equals("todo")){
            tabLayoutMediator=
                TabLayoutMediator(binding.tab,binding.pager,{tab,position-> tab.text= "todo" })
        }
        else {
            tabLayoutMediator=
                TabLayoutMediator(binding.tab,binding.pager,{tab,position-> tab.text=tabTitle[position] })
        }

        tabLayoutMediator.attach()


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }



}