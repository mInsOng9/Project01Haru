package com.song.project01haru.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.R
import com.song.project01haru.databinding.FragmentEditTodoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class EditTodoFragment():Fragment() {


    lateinit var binding: FragmentEditTodoBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

    lateinit var date:String
    lateinit var time:String
    lateinit var todo:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentEditTodoBinding.inflate(layoutInflater)
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

        date=sdf.format(Date())
        binding.tvDate.text=date
        binding.tvDate.setOnClickListener { calDialog() }

        time=SimpleDateFormat("hh:mm a").format(Date())
        binding.tvTime.text=time
        binding.tvTime.setOnClickListener { timeDialog() }

        todo=" "
        binding.tvOk.setOnClickListener { uploadDB() }

    }//onViewCreated(..)


    fun uploadDB() {
        todo=binding.etTask.text.toString()
        val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

            //var binding = FragmentEditTodoBinding.inflate(layoutInflater)
            //Toast.makeText(this, ""+dates, Toast.LENGTH_SHORT).show()
            val call: Call<String> = builder.setTodoItem(
                date,
                time,
                todo
            )
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var a: String? = response.body()
                    Toast.makeText(requireContext(), "" + a, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(requireContext(), "fail : ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
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

    fun timeDialog(){
        val dialog : AlertDialog = AlertDialog.Builder(requireActivity()).setView(R.layout.dialog_time).create()
        dialog.show()

        val tvOk: TextView?=dialog.findViewById<TextView>(R.id.tv_ok)
        val tvCancel: TextView?=dialog.findViewById(R.id.tv_cancel)
        val timePicker=dialog.findViewById<TimePicker>(R.id.time_picker)

        timePicker?.setOnTimeChangedListener { timePicker, hour,min ->  // 오전 / 오후 를 확인하기 위한 if 문
            var hour=hour
            if (hour > 12) {
                hour -= 12
                binding.tvTime.text= ""+hour + ":" + min + " pm"
            } else {
                binding.tvTime.text= ""+hour + ":" + min + " am"
            }
        }
        tvOk?.setOnClickListener {
            dialog.dismiss()
        }
        tvCancel?.setOnClickListener {
            binding.tvTime.text=SimpleDateFormat("hh:mm a").format(Date())
            dialog.dismiss()
        }

    }//timeDialog


}