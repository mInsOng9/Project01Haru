package com.song.project01haru.main.expinc

import java.util.*

data class ExpIncItem(
    var date: String,
    var totalInc:String,
    var totalExp:String,
    var total:String,
    var detailItems:MutableList<ExpIncDetailItem>?
    ) {
    constructor() : this("","","","",null)
}