package com.song.project01haru.edit

import com.song.project01haru.ProfileItem
import com.song.project01haru.main.expinc.ExpIncItem
import com.song.project01haru.main.todo.TodoItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    //Login
    @GET("haru/login/insertDB.php")
    fun setLoginItem(
        @Query("act") act: String?,
        @Query("email") email: String?,
        @Query("name") name: String?,
        @Query("img") img: String?
    ): Call<String>

    @GET("haru/login/loadDB.php")
    fun getLoginItem(): Call<ArrayList<ProfileItem>>

    //ExpInc
    @GET("haru/exp/insertDB.php")
    fun setExpincItem(
        @Query("date") date: String?,
        @Query("incTotal") inc: String?,
        @Query("expTotal") exp: String?,
        @Query("total") total: String?,
        @Query("account") account: String?,
        @Query("type") type: String?,
        @Query("category") category: String?,
        @Query("note") note: String?,
        @Query("amount") amount: String?,
    ): Call<String>

    @GET("haru/exp/loadDB.php")
    fun getExpIncItem(): Call<ArrayList<ExpIncItem>>

    //TodoList
    @GET("haru/todo/insertDB.php")
    fun setTodoItem(
        @Query("date") date: String?,
        @Query("time") time: String?,
        @Query("todo") todo: String?
    ): Call<String>

    @GET("haru/todo/loadDB.php")
    fun getTodoItem(@Query("date") date:String?): Call<ArrayList<TodoItem>>

    @GET("haru/todo/loadDB.php")
    fun getTodoItem2(@Query("date") date:String?): Call<String>



}
