package com.song.project01haru.main.todo

data class TodoItem(
    var act:String,
    var date:String,
    var time:String,
    var todo:String
) {
    constructor() : this("","","","")
}