package com.song.project01haru

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {


    fun retrofitInstance(): Retrofit {
        val baseUrl="http://mins22.dothome.co.kr"

        val builder = Retrofit.Builder()
        builder.baseUrl(baseUrl)
        builder.addConverterFactory(GsonConverterFactory.create())

        return builder.build()
    }

}