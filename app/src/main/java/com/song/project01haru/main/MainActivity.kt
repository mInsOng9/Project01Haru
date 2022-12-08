package com.song.project01haru.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.shuhart.materialcalendarview.*
import com.shuhart.materialcalendarview.MaterialCalendarView.Companion.SELECTION_MODE_RANGE
import com.song.project01haru.*
import com.song.project01haru.R
import com.song.project01haru.databinding.*
import com.song.project01haru.edit.EditActivity
import com.song.project01haru.edit.EditDiaryActivity
import com.song.project01haru.main.diary.DiaryFragment
import com.song.project01haru.main.expinc.ExpIncFragment
import com.song.project01haru.main.home.HomeFragment
import com.song.project01haru.main.skd.SkdFragment
import com.song.project01haru.main.todo.TodoFragment
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var drawerToggle:ActionBarDrawerToggle

    //bottomNavigationView
    val bnv by lazy { binding.bnv}

    //fragment
    var fragments=ArrayList<Fragment?>()
    lateinit var fragmentManager:FragmentManager

    //abt calendar view
    var date= Date()
    val calendar by lazy{binding.calendarView}
    lateinit var day:CalendarDay
   // val calendar by lazy {binding.calendarView}
    val sdf by lazy {SimpleDateFormat("yyyy.MM")}

    //bundle (to send data to fragments)
    lateinit var bundle:Bundle

    var holidays:MutableList<JSONObject> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar
        val toolbar: androidx.appcompat.widget.Toolbar =binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Title(date)
        binding.tvTitleDate.setOnClickListener { calDialog() }

        //drawerlayout
        val drawerLayout:DrawerLayout=binding.drawerLayout
        val nav:NavigationView=binding.nav
        drawerToggle= ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerToggle.syncState()
        drawerToggle.drawerArrowDrawable

        //drawer haeder listener
        nav.getHeaderView(0).setOnClickListener{ startActivity(Intent(this, ProfileActivity::class.java))}
        
        //navigation item listener
        nav.setNavigationItemSelectedListener (NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.menu_dnav_notif -> startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS))
                R.id.menu_dnav_update -> {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(
                            "https://play.google.com/store/apps/details?id=com.example.android")
                        setPackage("com.android.vending")
                    }
                    startActivity(intent)
                }
                R.id.menu_dnav_ctc ->startActivity(Intent(this, ContactUsActivity::class.java))
                else -> {
                    Toast.makeText(this@MainActivity, "else", Toast.LENGTH_SHORT).show()}
            }
            drawerLayout.closeDrawer(nav)
            false
        })

        //fragment
        //first fragment to show
        binding.bnv.selectedItemId= R.id.menu_bnv_home
        fragments.add(ExpIncFragment())
        fragments.add(TodoFragment())
        fragments.add(HomeFragment())
        fragments.add(SkdFragment())
        fragments.add(DiaryFragment())
        //fragment manager
        fragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.frag_container,fragments[0]!!).hide(fragments[0]!!).commit()
        fragmentManager.beginTransaction().add(R.id.frag_container,fragments[1]!!).hide(fragments[1]!!).commit()
        fragmentManager.beginTransaction().add(R.id.frag_container,fragments[2]!!).commit()
        fragmentManager.beginTransaction().add(R.id.frag_container,fragments[3]!!).hide(fragments[3]!!).commit()
        fragmentManager.beginTransaction().add(R.id.frag_container,fragments[4]!!).hide(fragments[4]!!).commit()

        //bottom navigation view (fragment)
        bnv.setOnItemSelectedListener {it->
            val tran:FragmentTransaction=fragmentManager.beginTransaction()

            if(fragments[0]!=null) tran.hide(fragments[0]!!)
            if(fragments[1]!=null) tran.hide(fragments[1]!!)
            if(fragments[2]!=null) tran.hide(fragments[2]!!)
            if(fragments[3]!=null) tran.hide(fragments[3]!!)
            if(fragments[4]!=null) tran.hide(fragments[4]!!)

            when(it.itemId){
                R.id.menu_bnv_expInc -> tran.show(fragments[0]!!)
                R.id.menu_bnv_todo -> tran.show(fragments[1]!!)
                R.id.menu_bnv_home -> tran.show(fragments[2]!!)
                R.id.menu_bnv_schd -> tran.show(fragments[3]!!)
                R.id.menu_bnv_diary -> tran.show(fragments[4]!!)
            }
            tran.commit()
            true
        }


        //floating action button
        binding.fabAddExpinc.visibility=View.GONE
        binding.fabAddTodo.visibility=View.GONE
        binding.fabAddSkd.visibility=View.GONE
        binding.fabAddDiary.visibility=View.GONE

        var fabVisible:Boolean=true
        binding.fabAdd.setOnClickListener({
            if(fabVisible){
                binding.fabAddExpinc.show()
                binding.fabAddTodo.show()
                binding.fabAddSkd.show()
                binding.fabAddDiary.show()

                fabVisible=false
            }
            else{
                binding.fabAddExpinc.hide()
                binding.fabAddTodo.hide()
                binding.fabAddSkd.hide()
                binding.fabAddDiary.hide()

                fabVisible=true
            }
        })
        val intent=Intent(this, EditActivity::class.java)
        binding.fabAddExpinc.setOnClickListener({
            intent.putExtra("frag","expinc")
            startActivity(intent)
        })
        binding.fabAddTodo.setOnClickListener({
            intent.putExtra("frag","todo")
            startActivity(intent)
        })
        binding.fabAddSkd.setOnClickListener({
            intent.putExtra("frag","skd")
            startActivity(intent)
        })
        binding.fabAddDiary.setOnClickListener({
            startActivity(Intent(this, EditDiaryActivity::class.java))
        })

        //calendar listener to change title date
        calendar.addOnMonthChangedListener(object:OnMonthChangedListener{
            override fun onMonthChanged(widget: MaterialCalendarView, date: CalendarDay) {
                binding.tvTitleDate.text=date.year.toString()+"."+(date.month+1).toString()

                loadHoliday(date.year.toString()+(date.month+1).toString())

            }

        })
        //calendar range selection Listener
        calendar.addOnRangeSelectedListener(object :OnRangeSelectedListener{
            override fun onRangeSelected(widget: MaterialCalendarView, dates: List<CalendarDay>) {
                binding.tvTitleDate.text =sdf.format(calendar.selectedDates?.get(0).date)

                val daySdf=SimpleDateFormat("yyyy-MM-dd")
                val days:MutableList<String> = mutableListOf()
                loadHoliday(SimpleDateFormat("yyyymm").format(calendar.selectedDate?.date!!))

                dates.forEach {
                   val s= daySdf.format( it.date )
                    days.add(s)
                }

                (fragments[0] as ExpIncFragment).changeDays(days)
                (fragments[1] as TodoFragment).changeDays(days)
                (fragments[2] as HomeFragment).changeDays(days)
                (fragments[3] as SkdFragment).changeDays(days)
                (fragments[4] as DiaryFragment).changeDays(days)

            }
        })
        //calendar oneday selection listener
        calendar.addOnDateChangedListener(object : OnDateSelectedListener{
            override fun onDateSelected(
                widget: MaterialCalendarView,
                date: CalendarDay,
                selected: Boolean
            ) {
                this@MainActivity.date=Date()
                binding.tvTitleDate.text=sdf.format(calendar.selectedDate?.date)
                loadHoliday(SimpleDateFormat("yyyymm").format(calendar.selectedDate?.date!!))

                (fragments[0] as ExpIncFragment).changeDay(calendar.selectedDate?.date!!)
                (fragments[1] as TodoFragment).changeDay(calendar.selectedDate?.date!!)
                (fragments[2] as HomeFragment).changeDay(calendar.selectedDate?.date!!)
                (fragments[3] as SkdFragment).changeDay(calendar.selectedDate?.date!!)
                (fragments[4] as DiaryFragment).changeDay(calendar.selectedDate?.date!!)

            }
        })

        calendar.monthIndicatorVisible=false
        calendar.selectionMode= SELECTION_MODE_RANGE






        //            override fun onResponse(call: Call<RestItem>, response: Response<RestItem>) {
//                response.body()?.locdate?.let { Log.e("date", it) }
//                response.body()?.dateName?.let{Log.e("name",it)
//                    Toast.makeText(this@MainActivity, "adsf"+it, Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<RestItem>, t: Throwable) {
//                Log.e("err",t.message.toString())
//            }
//
//


//        builder.getHoliday2(
//            apiKey,
//            SimpleDateFormat("yyyy").format(date),
//            SimpleDateFormat("MM").format(date)
//        ).enqueue(object :Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                Log.e("res",response.body().toString())
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.e("err",t.message.toString())
//            }
//
//        })
//        thread(start=true) {
//
//            lateinit var restItem: RestItem
//            lateinit var restItems: MutableList<RestItem>
//            lateinit var locdate: String
//            lateinit var dateName: String
//

//            //json parsing
//            restItems = mutableListOf()
//            val url: URL = URL(urlAddress)
//            Log.e("url", urlAddress)
//
//            var reader: BufferedReader = BufferedReader(InputStreamReader(url.openStream()))
//            var buffer: StringBuffer = StringBuffer()
//            while (true) {
//                var line = reader.readLine()
//                if (line == null) break;
//                buffer.append(line + "\n")
//            }
//
//            runOnUiThread {
//                var jsonArray: JSONArray = JSONArray(buffer.toString())
//
//                for (i in 0 until jsonArray.length()) {
//                    var jo: JSONObject = jsonArray.getJSONObject(i)
//
//                    var body: JSONObject = jo.getJSONObject("body")
//                    var items: JSONObject = body.getJSONObject("items")
//                    var item: JSONObject = items.getJSONObject("item")
//                    locdate = item.get("locdate").toString()
//                    dateName = item.get("dateName").toString()
//
//                }
//                Toast.makeText(this@MainActivity, "" + locdate + "df:" + dateName, Toast.LENGTH_SHORT).show()
//            }
//        }
    }


    fun calDialog(){
        val dialog : AlertDialog=AlertDialog.Builder(this).setView(R.layout.dialog_date).create()
        dialog.show()

        val tvOk:TextView?=dialog.findViewById<TextView>(R.id.tv_ok)
        val tvCancel:TextView?=dialog.findViewById(R.id.tv_cancel)
        val calendarView=dialog.findViewById<MaterialCalendarView>(R.id.calDialog)

        tvOk?.setOnClickListener {
            binding.tvTitleDate.text=sdf.format(calendar.selectedDate?.date!!)
            loadHoliday(SimpleDateFormat("yyyymm").format(calendar.selectedDate?.date!!))

            dialog.dismiss()
        }

        tvCancel?.setOnClickListener { dialog.dismiss() }

    }//calDialog

    fun loadHoliday(day:String){
        var urlAddress: String =
            "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/"
        val apiKey="ree7QcEjSF8SAguLrEw9p1nb5SEGKDvhb8PnvaPqJP7N8meanZVpJsQNxDlrGDTzprvGOrbs/v/TsELdXsuF5w=="

        val builder= Retrofit.Builder().baseUrl(urlAddress)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        builder.getHoliday(
            apiKey,
            day.substring(0,4),
            day.substring(4,6)
        ).enqueue(object : Callback<String>{

            override fun onResponse(call: Call<String>, response: Response<String>) {

                Toast.makeText(this@MainActivity, "day: "+day, Toast.LENGTH_SHORT).show()

                Log.i("holi", response?.body().toString())
                var jo = JSONObject(response.body())
                var resJo = jo.getJSONObject("response")
                var bodyJo = resJo.getJSONObject("body")
                if(bodyJo.get("items")=="") return
                var items = bodyJo.getJSONObject("items")


                if (bodyJo.get("totalCount").toString() == "1") {
                    var item = items.getJSONObject("item")
                    Toast.makeText(this@MainActivity, "date: "+item, Toast.LENGTH_SHORT).show()

                    holidays[0] = item

                    Log.i("holiday", holidays[0].toString())
                }
                else{
                    var item=items.getJSONArray("item")
                    for(i in 0 until item.length()){
                        var item2=item.getJSONObject(i)

                        holidays[i]=item2
                        Log.i("holiday", holidays.toString())

                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("err",t.message.toString())
            }
        })

    }//loadHoliday()
}





