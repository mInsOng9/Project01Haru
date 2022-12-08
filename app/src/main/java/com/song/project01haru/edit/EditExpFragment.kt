package com.song.project01haru.edit

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.G
import com.song.project01haru.R
import com.song.project01haru.RetrofitService
import com.song.project01haru.databinding.FragmentEditExpBinding
import com.song.project01haru.main.expinc.ExpIncAdapter
import com.song.project01haru.main.expinc.ExpIncItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class EditExpFragment : Fragment() {

    lateinit var binding: FragmentEditExpBinding
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
    lateinit var date:String
    var amount:Double = 0.0
    var account:Int = R.drawable.type_card
    lateinit var type:String
    lateinit var category:String
    lateinit var note:String

    var items:MutableList<BsdItem> = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?) : View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_edit_exp, container, false)
    }

    //위 onCreateView()메소드를 통해 만들어진 뷰 안에 값들을 제어하기 위해 자동으로 실행되는 메소드
    //View가 만들어진 후 자동으로 실행되는 메소드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentEditExpBinding.bind(view)

        date=sdf.format(Date())
        binding.tvDate.text=date
        binding.tvDate.setOnClickListener { calDialog() }
        items.add(BsdItem("cash"))
        items.add(BsdItem("card"))
        binding.tvAccount.setOnClickListener { selectAct() }
        account=R.drawable.type_card
        type=" "
        category=" "
        note=" "
        binding.tvOk.setOnClickListener { uploadDB() }
        binding.tvCancel.setOnClickListener { requireActivity().finish() }



    }
    fun selectAct(){
        val adapter=BsdAdapter(requireActivity(),items)
        var bottomSheetDialog=BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_dialog)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        items.add(items.size,BsdItem("  +  "))

        bottomSheetDialog.findViewById<RecyclerView>(R.id.recycler)?.adapter=adapter
        bottomSheetDialog.show()

       adapter.setOnItemclickListner(object: BsdAdapter.OnItemClickListner{
            override fun onItemClick(view: View, position: Int) {
                if (position==items.size-1){
                    var dialog=AlertDialog.Builder(requireActivity()).setView(R.layout.dialog_account).create()
                    dialog.show()

                    dialog.findViewById<TextView>(R.id.tv_ok)?.setOnClickListener {
                        if(dialog.findViewById<TextView>(R.id.tv_type)!!.text!=null){
                            binding.tvAccount.text= dialog.findViewById<TextView>(R.id.tv_type)!!.text.toString()
                            items.add(items.size-1,BsdItem(binding.tvAccount.text.toString()))
                            adapter.notifyDataSetChanged()
                            dialog.dismiss()
                        }
                    }

                }
                else{
                    binding.tvAccount.text=items[position].act
                    bottomSheetDialog.dismiss()

                }
                if(binding.tvAccount.text.equals("card") || binding.tvAccount.text.equals("cash")) type=""
                else type=binding.tvAccount.text.toString()

            }
        })

    }

    fun uploadDB() {

        if(binding.tvAccount.text.toString().equals("card")) account= R.drawable.type_card
        else if(binding.tvAccount.text.toString().equals("cash")) account=R.drawable.type_cash

        category=binding.etCategory.text.toString()
        note=binding.etNotes.text.toString()
        amount=binding.etAmount.text.toString().toDouble()
        val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        val call: Call<String> = builder.setExpItem(
            G.act,
            date,
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