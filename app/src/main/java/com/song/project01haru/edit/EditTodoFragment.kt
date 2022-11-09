package com.song.project01haru.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.song.project01haru.RetrofitHelper
import com.song.project01haru.databinding.FragmentEditTodoBinding
import com.song.project01haru.main.todo.TodoItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditTodoFragment:Fragment() {
    lateinit var binding: FragmentEditTodoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentEditTodoBinding.inflate(layoutInflater)

        val builder = Retrofit.Builder()
        builder.baseUrl("http://mins22.dothome.co.kr")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val call: Call<TodoItem> = retrofitService.getTodoItem(
            "2022.10.20",
            "10:00",
            "sleep"
        )

        call.enqueue(object : Callback<TodoItem?> {
            override fun onResponse(call: Call<TodoItem?>, response: Response<TodoItem?>) {
                val item: TodoItem? = response.body()

            }

            override fun onFailure(call: Call<TodoItem?>, t: Throwable) {
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentEditTodoBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}