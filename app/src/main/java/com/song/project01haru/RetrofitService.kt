package com.song.project01haru

import com.song.project01haru.ProfileItem
import com.song.project01haru.main.diary.DiaryItem
import com.song.project01haru.main.expinc.ExpIncItem
import com.song.project01haru.main.home.HomeItem
import com.song.project01haru.main.skd.EventItem
import com.song.project01haru.main.skd.SkdEvtItem
import com.song.project01haru.main.skd.SkdItem
import com.song.project01haru.main.todo.TodoItem
import retrofit2.Call
import retrofit2.http.DELETE
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

    //contact us
    @GET("haru/contact/insertDB.php")
    fun setContactUsItem(
        @Query("act") act: String?,
        @Query("comment") comment: String?
    ): Call<String>

    //Home
    @GET("haru/loadHome.php")
    fun getHomeItem(@Query("act") act:String?,@Query("date") date:String?): Call<String>

    //ExpInc
    @GET("haru/exp/insertDB.php")
    fun setExpincItem(
        @Query("act") act:String?,
        @Query("date") date: String?,
        @Query("incTotal") inc: String?,
        @Query("expTotal") exp: String?,
        @Query("total") total: String?,
        @Query("amount") amount: String?,
        @Query("account") account: Int?,
        @Query("type") type: String?,
        @Query("category") category: String?,
        @Query("note") note: String?
    ): Call<String>

    @GET("haru/exp/loadDB.php")
    fun getExpIncItem(@Query("act") act:String?,@Query("date") date:String?): Call<ArrayList<ExpIncItem>>

    //TodoList
    @GET("haru/todo/insertDB.php")
    fun setTodoItem(
        @Query("act") act:String?,
        @Query("date") date: String?,
        @Query("time") time: String?,
        @Query("todo") todo: String?
    ): Call<String>

    @GET("haru/todo/loadDB.php")
    fun getTodoItem(@Query("act") act:String?,@Query("date") date:String?): Call<ArrayList<TodoItem>>

    @GET("haru/todo/loadDB.php")
    fun getTodoItem2(@Query("date") date:String?): Call<String>


    //Schedule
    @GET("haru/skd/insertDB.php")
    fun setSkdItem(
        @Query("act") act:String?,
        @Query("date") date:String?,
        @Query("time") time:String?,
        @Query("skd") skd:String?,
        @Query("note") note:String?
    ):Call<String>

    @GET("haru/skd/loadDB.php")
    fun getSkdItem(@Query("act") act:String?,@Query("date") date:String?):Call <String>

    @DELETE("haru/skd/deleteDB.php")
    fun deleteSkdItem(@Query("act") act: String?, @Query("date") date: String?):Call<String>
    //event
    @GET("haru/event/insertDB.php")
    fun setEvtItem(
        @Query("act") act:String?,
        @Query("date") date:String?,
        @Query("event") evt:String?,
    ):Call<String>

   // @GET("haru/skd/loadDB.php")
    //fun getEvtItem(@Query("act") act:String?,@Query("date") date:String?):Call <ArrayList<EventItem>>

    //Diary
    @GET("haru/diary/insertDB.php")
    fun setDiaryItem(
        @Query("act") act:String?,
        @Query("date") date:String?,
        @Query("icon") icon:String?,
        @Query("content") content:String?,
        @Query("img") img:String?,
        @Query("map_lat") map_lat:Double?,
        @Query("map_long") map_long:Double?,
    ):Call<String>

    @GET("haru/diary/loadDB.php")
    fun getDiaryItem(@Query("act") act:String?,@Query("date") date:String?):Call <ArrayList<DiaryItem>>

    @GET("getHoliDeInfo")
    fun getHoliday(
        @Query("ServiceKey")apiKey:String,
        @Query("solYear")year:String,
        @Query("solMonth") month:String,
        @Query("_type") type:String="json"
    ):Call<RestItem>

    @GET("getHoliDeInfo")
    fun getHoliday2(
        @Query("ServiceKey")apiKey:String,
        @Query("solYear")year:String,
        @Query("solMonth") month:String,
        @Query("_type") type:String="json"
    ):Call<String>

}
