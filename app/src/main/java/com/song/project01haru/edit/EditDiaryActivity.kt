package com.song.project01haru.edit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.G
import com.song.project01haru.R
import com.song.project01haru.RetrofitService
import com.song.project01haru.databinding.ActivityEditDiaryBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
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
        binding.ivAddPhoto.setOnClickListener {
            if(binding.ivPhoto1!=null && binding.ivPhoto2!=null){
                photoDialog.show()
            }
            else{
                var intent=Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                resultLauncher.launch(intent)
            }
        }
        binding.ivPhoto1.setOnClickListener { photoDialog.show() }
        binding.ivPhoto2.setOnClickListener { photoDialog.show() }
    }
    val photoDialog:AlertDialog= AlertDialog.Builder(this).setView(R.layout.dialog_photo).create()

    val resultLauncher: ActivityResultLauncher<Intent> =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>(){ result ->
        if(result.resultCode!= RESULT_CANCELED){
            var intent=result.data
            var uri=intent?.data
            if(binding.ivPhoto1==null){
                Glide.with(this).load(uri).into(binding.ivPhoto1)
                Glide.with(this).load(uri).into(photoDialog.findViewById<>())
            }
            else{
                if(binding.ivPhoto2==null){
                    Glide.with(this).load(uri).into(binding.ivPhoto2)
                    Glide.with(this).load(uri).into(photoDialog.findViewById<>())

                }
                else{
                    photoDialog.show()

                }
            }
            imgPath= getRealPathFromUri(uri).toString()
        }
    })

    lateinit var imgPath:String

    fun getRealPathFromUri(uri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(
            this,
            uri!!, proj, null, null, null
        )
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result

    }//getRealPathFromUri

    fun uploadDB() {

        content=binding.etDiary.text.toString()

        val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        var file= File(imgPath)
        var requestBody=RequestBody.create(MediaType.parse("image/*"),file)
        var part:MultipartBody.Part=MultipartBody.Part.createFormData("img",file.name,requestBody)

        val call: Call<String> = builder.setDiaryItem(
            G.act,
            date,
            icon,
            content,
            part,
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