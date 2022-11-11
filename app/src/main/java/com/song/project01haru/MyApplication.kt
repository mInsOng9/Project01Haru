package com.song.project01haru

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,"547da376bcedc1ad60f47ee06b63e9ed")
    }

}