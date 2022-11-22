package com.song.project01haru.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.G
import com.song.project01haru.R
import com.song.project01haru.RetrofitService
import com.song.project01haru.databinding.FragmentEditIncBinding
import com.song.project01haru.main.expinc.ExpIncItem
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
    lateinit var exp:String
    lateinit var total:String
    lateinit var amount:String
    var account:Int = R.drawable.type_card
    lateinit var type:String
    lateinit var category:String
    lateinit var note:String

    lateinit var mosInc:String

    var items:MutableList<BsdItem> = mutableListOf()

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

        binding.tvAccount.setOnClickListener { selectAct() }
        items.add(BsdItem("cash"))
        items.add(BsdItem("card"))
        exp="0"
        inc="0"
        total="0"
        amount="0"
        account=R.drawable.type_card
        type=" "
        category=" "
        note=" "

        mosInc="0"

        binding.tvOk.setOnClickListener { uploadDB() }
        binding.tvCancel.setOnClickListener { requireActivity().finish() }

    }//onCreateView(..)
    fun selectAct(){
        var bottomSheetDialog= BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_dialog)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.show()
        val adapter=BsdAdapter(requireActivity(),items)

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

    }//selectAct()
    fun loadDB(){
        val builder= Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitService::class.java)

        val call: Call<ArrayList<ExpIncItem>> = builder.getExpIncItem(G.act,date)

        call.enqueue(object : Callback<ArrayList<ExpIncItem>> {
            override fun onResponse(
                call: Call<ArrayList<ExpIncItem>>,
                response: Response<ArrayList<ExpIncItem>>
            ) {
                val items: ArrayList<ExpIncItem> = response.body()!!

                for (item in items) {
                    exp=item.totalExp
                }
            }
            override fun onFailure(call: Call<ArrayList<ExpIncItem>>, t: Throwable) {
            }
        })
    }//loadDB()
    fun uploadDB() {
        loadDB()
        inc =binding.etAmount.text.toString()
        total= (exp.toInt()+inc.toInt()).toString()
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
            exp,
            total,
            "+"+amount,
            20,
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

    }//calDialog()

}//EditIncFragment