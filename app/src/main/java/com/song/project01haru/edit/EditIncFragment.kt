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
import com.song.project01haru.databinding.FragmentEditIncBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class EditIncFragment : Fragment() {

    lateinit var binding: FragmentEditIncBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
    lateinit var date:String
    lateinit var inc:String
    //lateinit var exp:String
    lateinit var total:String
    lateinit var amount:String
    var account:Int = R.drawable.type_card
    lateinit var type:String
    lateinit var category:String
    lateinit var note:String

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
//        return inflater.inflate(R.layout.activity_edit_inc_fragment, container, false)
        binding = FragmentEditIncBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentEditIncBinding.bind(view)

        date= sdf.format(Date())
        binding.tvDate.text=date
        binding.tvDate.setOnClickListener { calDialog() }

        inc=" "
        total=" "
        amount=" "
        account=R.drawable.type_card
        type=" "
        category=" "
        note=" "
        binding.tvOk.setOnClickListener { uploadDB() }
        binding.tvCancel.setOnClickListener { requireActivity().finish() }

    }

    fun uploadDB() {
        inc =binding.etAmount.text.toString()
        total= "6000"//(toInt()+inc.toInt()).toString()
        amount=binding.etAmount.text.toString()

        if(binding.tvAccount.text.toString().equals("card")) account= R.drawable.type_card
        else if(binding.tvAccount.text.toString().equals("cash")) account=R.drawable.type_cash

        category=binding.etCategory.text.toString()
        note=binding.etNotes.text.toString()

        val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        val call: Call<String> = builder.setExpincItem(
            G.act,
            date,
            inc,
            "9000",
            total,
            amount,
            account,
            type,
            category,
            note
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