package com.song.project01haru.main.expinc

data class ExpIncItem(
    var act:String,
    var date:String,
    var totalInc:String,
    var totalExp:String,
    var total:String,
    var amount:String,
    var account: Int?,
    var type:String,
    var category:String,
    var note:String,

    ) {
    constructor() : this("","","","","","",null,"","","")
}