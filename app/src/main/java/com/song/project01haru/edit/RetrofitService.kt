package com.song.project01haru.edit

import com.song.project01haru.main.todo.TodoItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("haru/insertDB.php")
    fun setTodoItem(
        @Query("date") date: String?,
        @Query("time") time: String?,
        @Query("todo") todo: String?
    ): Call<String>

    @GET("haru/loadDB.php")
    fun getTodoItem(): Call<ArrayList<TodoItem>>

}