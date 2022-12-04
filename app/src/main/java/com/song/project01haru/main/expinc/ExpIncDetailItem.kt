package com.song.project01haru.main.expinc

data class ExpIncDetailItem(
    var date:String,
    var amount:Double,
    var account: Int?,
    var type:String,
    var category:String,
    var note:String,

    ) {
    constructor() : this("",0.0,null,"","","")
}