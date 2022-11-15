package com.song.project01haru.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.G
import com.song.project01haru.R
import com.song.project01haru.RetrofitService
import com.song.project01haru.databinding.FragmentEditEventBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class EditEventFragment :Fragment() {
    lateinit var binding:FragmentEditEventBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
    lateinit var date:String
    lateinit var evt:String

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

        date=sdf.format(Date())
        binding.tvDate.text=date
        binding.tvDate.setOnClickListener { calDialog() }

        evt= " "
        binding.tvOk.setOnClickListener { uploadDB() }

        binding.tvCancel.setOnClickListener { requireActivity().finish() }
    }//onViewCreated(..)

    fun uploadDB() {
        evt= binding.etEvt.text.toString()
        val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        val call: Call<String> = builder.setEvtItem(
            G.act,
            date,
            evt
        )
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var a:String?=response.body()
                Toast.makeText(requireContext(), ""+a, Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }

        })
    }//uploadDB()

fun calDialog(){
    val dialog : AlertDialog = AlertDialog.Builder(requireActivity()).setView(R.layout.dialog_date).create()
    dialog.show()

    val tvOk: TextView?=dialog.findViewById<TextView>(R.id.tv_ok)
    val tvCancel: TextView?=dialog.findViewById(R.id.tv_cancel)
    val calendarView=dialog.findViewById<MaterialCalendarView>(R.id.calDialog)

    tvOk?.setOnClickListener {
        binding.tvDate.text=sdf.format(calendarView?.selectedDate?.date)
        date=binding.tvDate.text.toString()
        dialog.dismiss()
    }
    tvCancel?.setOnClickListener { dialog.dismiss() }

}//calDialog
}