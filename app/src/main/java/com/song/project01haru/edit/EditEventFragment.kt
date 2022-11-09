package com.song.project01haru.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.R
import com.song.project01haru.databinding.FragmentEditEventBinding
import com.song.project01haru.databinding.FragmentEditExpBinding
import java.text.SimpleDateFormat
import java.util.*

class EditEventFragment :Fragment() {
    lateinit var binding:FragmentEditEventBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentEditEventBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding= FragmentEditEventBinding.bind(view)

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