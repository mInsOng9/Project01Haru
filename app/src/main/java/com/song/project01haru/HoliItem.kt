package com.song.project01haru

data class HoliResponse constructor(
    var items:MutableList<HoliItem>
)
data class HoliItem (
    var header:String?,
    var body:MutableList<BodyItem>
)
data class BodyItem(
    var items:MutableList<HoliItems>,
    var totalCount:String?
)
data class HoliItems(
    var locdate:String?,
    var dateName:String
)
