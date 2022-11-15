package com.song.project01haru

data class RestItem (
    var locdate:String?,
    var dateName:String
){
    fun RestItem( locdate: String?, dateName: String){
        this.locdate=locdate
        this.dateName=dateName
    }
}
