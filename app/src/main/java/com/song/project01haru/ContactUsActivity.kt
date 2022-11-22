package com.song.project01haru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import com.song.project01haru.databinding.ActivityContactUsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ContactUsActivity : AppCompatActivity() {

    lateinit var binding:ActivityContactUsBinding
    lateinit var comment:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbar: androidx.appcompat.widget.Toolbar =binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        comment=""

        binding.tvOk.setOnClickListener { uploadDB() }
        binding.tvCancel.setOnClickListener { finish() }
    }

    fun uploadDB(){

        comment=binding.etComment.text.toString()
        val builder=Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        val call=builder.setContactUsItem(
            G.act,
            binding.etComment.text.toString()
        )
        call.enqueue(object:Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Toast.makeText(this@ContactUsActivity, ""+response.body(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}