package com.song.project01haru.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.R
import com.song.project01haru.databinding.ActivityEditDiaryBinding
import java.text.SimpleDateFormat
import java.util.*

class EditDiaryActivity : AppCompatActivity() {

    lateinit var binding:ActivityEditDiaryBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.tvDay.text=sdf.format(Date())
        binding.tvDay.setOnClickListener { calDialog() }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()

    }//onSupportNavigateUp()

    fun calDialog(){
        val dialog : AlertDialog = AlertDialog.Builder(this).setView(R.layout.dialog_date).create()
        dialog.show()

        val tvOk: TextView?=dialog.findViewById<TextView>(R.id.tv_ok)
        val tvCancel: TextView?=dialog.findViewById(R.id.tv_cancel)
        val calendarView=dialog.findViewById<MaterialCalendarView>(R.id.calDialog)

        tvOk?.setOnClickListener {
            binding.tvDay.text=sdf.format(calendarView?.selectedDate?.date)
            dialog.dismiss()
        }
        tvCancel?.setOnClickListener { dialog.dismiss() }

    }//calDialog()

}