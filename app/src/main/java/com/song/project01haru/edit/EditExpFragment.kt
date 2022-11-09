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
import com.song.project01haru.databinding.FragmentEditExpBinding
import java.text.SimpleDateFormat
import java.util.*

class EditExpFragment : Fragment() {

    lateinit var binding: FragmentEditExpBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?) : View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_edit_exp, container, false)
    }

    //위 onCreateView()메소드를 통해 만들어진 뷰 안에 값들을 제어하기 위해 자동으로 실행되는 메소드
    //View가 만들어진 후 자동으로 실행되는 메소드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentEditExpBinding.bind(view)

        binding.tvDate.text=sdf.format(Date())
        binding.tvDate.setOnClickListener { calDialog() }

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

}