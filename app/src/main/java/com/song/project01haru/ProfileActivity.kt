package com.song.project01haru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.song.project01haru.databinding.ActivityProfileBinding
import com.song.project01haru.edit.RetrofitService
import com.song.project01haru.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
        binding.tvId.text= intent.getLongExtra("id", 0).toString()

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.ivProfile)
        img=intent.getStringExtra("img").toString()

        binding.tvOk.setOnClickListener {
            uploadDB()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.tvCancel.setOnClickListener { finish() }
    }

    val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitService = builder.create(RetrofitService::class.java)

    fun uploadDB(){
        val call: Call<String> = retrofitService.setLoginItem(
//            "a","s","d",
//            "f"
            binding.tvId.text.toString(),
            binding.etEmail.hint.toString(),
            binding.etName.hint.toString(),
            img
        )

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var a: String? = response.body()
                Toast.makeText(this@ProfileActivity, "" + a, Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "fail : ${t.message}", Toast.LENGTH_SHORT).show()
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
                    binding.tvId.text=item.act
                    binding.etEmail.hint=item.email
                    binding.etName.hint=item.name
                    Glide.with(this@ProfileActivity ).load(item.img).error(R.drawable.profile).into(binding.ivProfile)
                }
            }
            override fun onFailure(call: Call<ArrayList<ProfileItem>>, t: Throwable) {
                //Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
