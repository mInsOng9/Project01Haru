package com.song.project01haru

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.song.project01haru.databinding.ActivityProfileBinding
import com.song.project01haru.main.MainActivity
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

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    lateinit var img:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadDB()
        binding.etEmail.hint = intent.getStringExtra("email").toString()
        binding.etName.hint = intent.getStringExtra("name").toString()
        binding.ivProfile.setOnClickListener {
            var intent=Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            resultLauncher.launch(intent)
        }
        Glide.with(this).load(intent.getStringExtra("img")).into(binding.ivProfile)
        img=intent.getStringExtra("img").toString()

        binding.tvOk.setOnClickListener {
            uploadDB()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.tvCancel.setOnClickListener { finish() }
    }//onCreate

    val resultLauncher: ActivityResultLauncher<Intent> =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>(){ result ->
            if(result.resultCode!= RESULT_CANCELED){
                var intent=result.data
                var uri=intent?.data

                Glide.with(this).load(uri).into(binding.ivProfile)

                imgPath= getRealPathFromUri(uri).toString()
            }
    })//resultLauncher

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

    val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitService = builder.create(RetrofitService::class.java)

    fun uploadDB(){
        var file= File(imgPath)
        var requestBody=RequestBody.create(MediaType.parse("image/*"),file)
        var part=MultipartBody.Part.createFormData("img",file.getName(),requestBody)
        val call: Call<String> = retrofitService.setLoginItem(
            G.act,
            binding.etEmail.hint.toString(),
            binding.etName.hint.toString(),
            part
        )

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var a: String? = response.body()
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun loadDB(){

        val call: Call<ArrayList<ProfileItem>> = retrofitService.getLoginItem()
        call.enqueue(object : Callback<ArrayList<ProfileItem>> {
            override fun onResponse(
                call: Call<ArrayList<ProfileItem>>,
                response: Response<ArrayList<ProfileItem>>
            ) {
                val items: ArrayList<ProfileItem> = response.body()!!

                for (item in items) {
                    binding.etEmail.hint=item.email
                    binding.etName.hint=item.name
                    Glide.with(this@ProfileActivity ).load(item.img).error(R.drawable.profile).into(binding.ivProfile)
                }
            }
            override fun onFailure(call: Call<ArrayList<ProfileItem>>, t: Throwable) {
            }
        })
    }
}
