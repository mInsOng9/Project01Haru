package com.song.project01haru

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.song.project01haru.databinding.ActivityLoginBinding
import com.song.project01haru.main.MainActivity

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    lateinit var pref:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref=getSharedPreferences("account", MODE_PRIVATE)


        //Key Hash
        val keyHash:String = Utility.getKeyHash(this)
        Log.d("keyHash",keyHash)

        if(!pref.getString("act","").equals("")){
            G.act=pref.getString("act","").toString()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.btnKakao.setOnClickListener { clickedLogin() }
    }

    private fun clickedLogin(){
        //callback function by login request
        val callback:(OAuthToken?,Throwable?) -> Unit = { token, error ->
            if(token != null){
                loadUserInfo()
            }
        }
        //check whether kakaotalk is installed in device or not
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            //request kakaotalk login
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            //request kakao account login
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }



    }

    fun loadUserInfo(){
        val intent=Intent(this, ProfileActivity::class.java)

        UserApiClient.instance.me { user, error ->

                if (user != null) {
                    val editor = pref.edit()
                    editor.putString("name", user.kakaoAccount?.profile?.nickname)
                    editor.putString("email", user.kakaoAccount?.email)
                    editor.putString("img", user.kakaoAccount?.profile?.profileImageUrl)
                    editor.putString("act", user.id.toString())
                    editor.commit()

                    intent.putExtra("name", user.kakaoAccount?.profile?.nickname)
                    intent.putExtra("email", user.kakaoAccount?.email)
                    G.act = user.id.toString()
                    intent.putExtra("img", user.kakaoAccount?.profile?.profileImageUrl)
                    startActivity(intent)
                }

        }
    }
}