package com.song.project01haru.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.R
import com.song.project01haru.databinding.FragmentEditTodoBinding
import java.text.SimpleDateFormat
import java.util.*


class EditTodoFragment():Fragment() {


    lateinit var binding: FragmentEditTodoBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")


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

        binding.tvDate.text=sdf.format(Date())
        binding.tvDate.setOnClickListener { calDialog() }

        binding.tvTime.text=SimpleDateFormat("hh:mm a").format(Date())
        binding.tvTime.setOnClickListener { timeDialog() }


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