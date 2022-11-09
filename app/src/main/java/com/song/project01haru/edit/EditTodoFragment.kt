package com.song.project01haru.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.R
import com.song.project01haru.RetrofitHelper
import com.song.project01haru.databinding.FragmentEditExpBinding
import com.song.project01haru.databinding.FragmentEditTodoBinding
import com.song.project01haru.main.todo.TodoItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class EditTodoFragment:Fragment() {
    lateinit var binding: FragmentEditTodoBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

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
        binding= FragmentEditTodoBinding.bind(view)

        binding.tvDate.text=sdf.format(Date())
        binding.tvDate.setOnClickListener { calDialog() }
    }//onViewCreated(..)

    fun calDialog(){
        val dialog : AlertDialog = AlertDialog.Builder(requireActivity()).setView(R.layout.dialog_date).create()
        dialog.show()

        val tvOk: TextView?=dialog.findViewById<TextView>(R.id.tv_ok)
        val tvCancel: TextView?=dialog.findViewById(R.id.tv_cancel)
        val calendarView=dialog.findViewById<MaterialCalendarView>(R.id.calDialog)

        tvOk?.setOnClickListener {
            binding.tvDate.text=sdf.format(calendarView?.selectedDate?.date)
            dialog.dismiss()
        }
        tvCancel?.setOnClickListener { dialog.dismiss() }

    }//calDialog
}