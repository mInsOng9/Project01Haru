package com.song.project01haru.main

import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
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
import com.shuhart.materialcalendarview.CalendarDay
import com.shuhart.materialcalendarview.MaterialCalendarView
import com.shuhart.materialcalendarview.MaterialCalendarView.Companion.SELECTION_MODE_RANGE
import com.shuhart.materialcalendarview.OnDateSelectedListener
import com.shuhart.materialcalendarview.OnRangeSelectedListener
import com.song.project01haru.*
import com.song.project01haru.databinding.*
import com.song.project01haru.edit.EditActivity
import com.song.project01haru.edit.EditDiaryActivity
import com.song.project01haru.main.diary.DiaryFragment
import com.song.project01haru.main.expinc.ExpIncFragment
import com.song.project01haru.main.home.HomeFragment
import com.song.project01haru.main.skd.SkdFragment
import com.song.project01haru.main.todo.TodoFragment
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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


        //range Listener
        calendar.addOnRangeSelectedListener(object :OnRangeSelectedListener{
            override fun onRangeSelected(widget: MaterialCalendarView, dates: List<CalendarDay>) {
                binding.tvTitleDate.text =sdf.format(calendar.selectedDates?.get(0).date)

                val weekdaySdf=SimpleDateFormat("yyyy-MM-dd")
                val days:MutableList<String> = mutableListOf()

                dates.forEach {
                   val s= weekdaySdf.format( it.date )
                    days.add(s)
                }

                (fragments[0] as ExpIncFragment).changeDays(days)
                (fragments[1] as TodoFragment).changeDays(days)
                (fragments[2] as HomeFragment).changeDays(days)
                (fragments[3] as SkdFragment).changeDays(days)
                (fragments[4] as DiaryFragment).changeDays(days)

            }
        })

        calendar.addOnDateChangedListener(object : OnDateSelectedListener{
            override fun onDateSelected(
                widget: MaterialCalendarView,
                date: CalendarDay,
                selected: Boolean
            ) {
                this@MainActivity.date=Date()
                binding.tvTitleDate.text=sdf.format(calendar.selectedDate?.date)

                (fragments[0] as ExpIncFragment).changeDay(calendar.selectedDate?.date!!)
                (fragments[1] as TodoFragment).changeDay(calendar.selectedDate?.date!!)
                (fragments[2] as HomeFragment).changeDay(calendar.selectedDate?.date!!)
                (fragments[3] as SkdFragment).changeDay(calendar.selectedDate?.date!!)
                (fragments[4] as DiaryFragment).changeDay(calendar.selectedDate?.date!!)

            }
        })

        calendar.monthIndicatorVisible=false
        calendar.selectionMode= SELECTION_MODE_RANGE





        //json parsing
//        var restItems:MutableList<RestItem> = mutableListOf()
//        val url:URL= URL("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo?" +
//                "ServiceKey="+"ree7QcEjSF8SAguLrEw9p1nb5SEGKDvhb8PnvaPqJP7N8meanZVpJsQNxDlrGDTzprvGOrbs%2Fv%2FTsELdXsuF5w%3D%3D"+
//                "&solYear=2015" +
//                "&solMonth=09" +
//                "&_type=json")
//        var reader:BufferedReader= BufferedReader(InputStreamReader(url.openStream(),"UTF-8"))
//        var buffer:StringBuffer=StringBuffer()
//        while(true){
//            var line=reader.readLine()
//            if(line==null) break;
//            buffer.append(line+"\n")
//        }
//
//        var jsonArray:JSONArray= JSONArray(buffer.toString())
//
//        for( i in 0 until jsonArray.length()){
//            var jo:JSONObject=jsonArray.getJSONObject(i)
//
//            var body:JSONObject=jo.getJSONObject("body")
//            var items:JSONObject=body.getJSONObject("items")
//            var item:JSONObject=items.getJSONObject("item")
//            var locdate:String=item.get("locdate").toString()
//            var dateName:String=item.get("dateName").toString()
//
//            var restItem=RestItem(locdate,dateName)
//            restItems.add(restItem)
//        }
//
//        Toast.makeText(this, restItems.toString(), Toast.LENGTH_SHORT).show()
//


    }


    //to hide fab
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    fun calDialog(){
        val dialog : AlertDialog=AlertDialog.Builder(this).setView(R.layout.dialog_date).create()
        dialog.show()

        val tvOk:TextView?=dialog.findViewById<TextView>(R.id.tv_ok)
        val tvCancel:TextView?=dialog.findViewById(R.id.tv_cancel)
        val calendarView=dialog.findViewById<MaterialCalendarView>(R.id.calDialog)

        tvOk?.setOnClickListener {
            binding.tvTitleDate.text=sdf.format(calendar.selectedDate?.date!!)
            dialog.dismiss()
        }

        tvCancel?.setOnClickListener { dialog.dismiss() }

    }//calDialog
}





