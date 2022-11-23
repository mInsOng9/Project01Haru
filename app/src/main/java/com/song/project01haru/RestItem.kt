package com.song.project01haru

data class RestResponse constructor(
    var items:MutableList<RestItem>
)
data class RestItem (
    var locdate:String?,
    var dateName:String
)
