package com.song.project01haru

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitHelper {
    var baseUrl: String

    //Retrofit 객체를 만들어서 리턴해주는 기능메소드 [ 객체 생성없이 사용가능하도록 ]
    fun getRetrofitInstance(): Retrofit? {
        baseUrl="http://mins22.dothome.co.kr"
        val builder = Retrofit.Builder()
        builder.baseUrl(baseUrl)
        builder.addConverterFactory(GsonConverterFactory.create())
        return builder.build()
    }
}