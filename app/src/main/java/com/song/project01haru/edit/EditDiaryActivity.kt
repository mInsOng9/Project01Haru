package com.song.project01haru.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.G
import com.song.project01haru.R
import com.song.project01haru.RetrofitService
import com.song.project01haru.databinding.ActivityEditDiaryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class EditDiaryActivity : AppCompatActivity() {

    lateinit var binding:ActivityEditDiaryBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

    lateinit var date:String
    lateinit var icon:String
    lateinit var content:String
    lateinit var img:String
    var map_lat:Double = 0.0
    var map_long:Double = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        date=sdf.format(Date())
        binding.tvDay.text=date
        binding.tvDay.setOnClickListener { calDialog() }

        icon=" "
        content=" "
        img=" "
        map_lat=0.0
        map_long=0.0


        binding.tvOk.setOnClickListener { uploadDB() }
        binding.tvCancel.setOnClickListener { finish() }
        binding.ivDiaryPhoto.setOnClickListener {  }
    }

    fun uploadDB() {

        content=binding.etDiary.text.toString()

        val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        val call: Call<String> = builder.setDiaryItem(
            G.act,
            date,
            icon,
            content,
            img,
            map_lat,
            map_long
        )
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var a:String?=response.body()
                Toast.makeText(this@EditDiaryActivity, ""+a, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }

        })
    }//uploadDB()

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()

    }//onSupportNavigateUp()

    fun calDialog(){
        val dialog : AlertDialog = AlertDialog.Builder(this).setView(R.layout.dialog_date).create()
        dialog.show()

        val tvOk: TextView?=dialog.findViewById<TextView>(R.id.tv_ok)
        val tvCancel: TextView?=dialog.findViewById(R.id.tv_cancel)
        val calendarView=dialog.findViewById<MaterialCalendarView>(R.id.calDialog)

        tvOk?.setOnClickListener {
            binding.tvDay.text=sdf.format(calendarView?.selectedDate?.date)
            date=binding.tvDay.text.toString()
            dialog.dismiss()
        }

        tvCancel?.setOnClickListener { dialog.dismiss() }

    }//calDialog()

}