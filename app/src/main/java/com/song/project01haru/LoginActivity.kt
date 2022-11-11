package com.song.project01haru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.song.project01haru.databinding.ActivityLoginBinding
import com.song.project01haru.databinding.FragmentEditTodoBinding
import com.song.project01haru.edit.EditActivity
import com.song.project01haru.edit.RetrofitService
import com.song.project01haru.main.MainActivity
import com.song.project01haru.main.todo.TodoAdapter
import com.song.project01haru.main.todo.TodoItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Key Hash
        val keyHash:String = Utility.getKeyHash(this)
        Log.d("keyHash",keyHash)

        binding.btnKakao.setOnClickListener { clickedLogin() }
    }

    private fun clickedLogin(){

        //callback function by login request
        val callback:(OAuthToken?,Throwable?) -> Unit = { token, error ->
            if(token != null){
                Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show()
                loadUserInfo()

            }
        }

        //check whether kakaotalk is installed in device or not
        if( UserApiClient.instance.isKakaoTalkLoginAvailable(this)){

            //request kakaotalk login
            UserApiClient.instance.loginWithKakaoTalk(this,callback= callback)

        }else{

            //request kakao account login
            UserApiClient.instance.loginWithKakaoAccount(this,callback=callback)
        }
    }
    fun loadUserInfo(){
        val intent=Intent(this, ProfileActivity::class.java)
        UserApiClient.instance.me { user, error ->
            if(user!=null){
                intent.putExtra("name",user.kakaoAccount?.profile?.nickname)
                intent.putExtra("email",user.kakaoAccount?.email)
                intent.putExtra("id",user.id)
                intent.putExtra("img",user.kakaoAccount?.profile?.profileImageUrl)
                Toast.makeText(this, ""+user.id.toString(), Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }

    }




}