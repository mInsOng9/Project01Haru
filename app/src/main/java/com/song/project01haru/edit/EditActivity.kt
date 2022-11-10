package com.song.project01haru.edit

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.song.project01haru.R
import com.song.project01haru.databinding.ActivityEditBinding
import com.song.project01haru.databinding.FragmentEditTodoBinding
import com.song.project01haru.main.todo.TodoItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

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


        binding.tvOk.setOnClickListener { uploadDB() }

    }

    fun uploadDB(){
        val builder = Retrofit.Builder()
        builder.baseUrl("http://mins22.dothome.co.kr")
        builder.addConverterFactory(ScalarsConverterFactory.create())
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        var binding=FragmentEditTodoBinding.inflate(layoutInflater)

        val call: Call<String> = retrofitService.setTodoItem(
//            "2020.10.20","10:00",
//            "hihih",
            binding.tvDate.text.toString(),
            binding.tvTime.text.toString(),
            binding.etTask.text.toString()
        )
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var a: String? =response.body()

                Toast.makeText(this@EditActivity, ""+a, Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@EditActivity, "fail : ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }



}