package com.song.project01haru.main.expinc

data class ExpIncItem(
    var day:String,
    var totalInc:String,
    var totalExp:String,
    var total:String,
    var account: Int?,
    var type:String,
    var category:String,
    var note:String,
    var amount:String

) {
    constructor() : this("","","","",null,"","","","")
}