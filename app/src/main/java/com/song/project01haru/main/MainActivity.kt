package com.song.project01haru.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

import com.shuhart.materialcalendarview.MaterialCalendarView
import com.song.project01haru.*
import com.song.project01haru.databinding.*
import com.song.project01haru.edit.EditActivity
import com.song.project01haru.edit.EditDiaryActivity
import com.song.project01haru.main.diary.DiaryFragment
import com.song.project01haru.main.expinc.ExpIncFragment
import com.song.project01haru.main.home.HomeFragment
import com.song.project01haru.main.skd.SkdFragment
import com.song.project01haru.main.todo.TodoFragment
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
    val date= Date()
    lateinit var day:String
    val calendar by lazy{binding.calendarView}
   // val calendar by lazy {binding.calendarView}
    val sdf by lazy {SimpleDateFormat("yyyy.MM")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar
        val toolbar: androidx.appcompat.widget.Toolbar =binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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
        fragments.add(HomeFragment())
        fragments.add(null)
        fragments.add(null)
        fragments.add(null)
        fragments.add(null)
        //fragment manager
        fragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.frag_container,fragments[0]!!).commit()

        //bottom navigation view
        bnv.setOnItemSelectedListener {it->
            val tran:FragmentTransaction=fragmentManager.beginTransaction()

            if(fragments[0]!=null) tran.hide(fragments[0]!!)
            if(fragments[1]!=null) tran.hide(fragments[1]!!)
            if(fragments[2]!=null) tran.hide(fragments[2]!!)
            if(fragments[3]!=null) tran.hide(fragments[3]!!)
            if(fragments[4]!=null) tran.hide(fragments[4]!!)
            when(it.itemId){
                R.id.menu_bnv_home -> tran.show(fragments[0]!!)
                R.id.menu_bnv_expInc -> {
                    if (fragments[1] == null) {
                        fragments[1] = ExpIncFragment()
                        tran.add(R.id.frag_container, fragments[1]!!)
                    }
                    tran.show(fragments[1]!!)
                }
                R.id.menu_bnv_todo -> {
                    if(fragments[2]==null){
                        fragments[2]= TodoFragment()
                        tran.add(R.id.frag_container,fragments[2]!!)
                    }
                    tran.show(fragments[2]!!)
                }
                R.id.menu_bnv_schd -> {
                    if(fragments[3]==null){
                        fragments[3]= SkdFragment()
                        tran.add(R.id.frag_container,fragments[3]!!)
                    }
                    tran.show(fragments[3]!!)
                }
                R.id.menu_bnv_diary -> {
                    if(fragments[4]==null){
                        fragments[4]= DiaryFragment()
                        tran.add(R.id.frag_container,fragments[4]!!)
                    }
                    tran.show(fragments[4]!!)
                }
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
        binding.fabAddDiary.setOnClickListener({startActivity(Intent(this, EditDiaryActivity::class.java))})



        //abt calendarView
        //simpleDateFormat.format(date);
        //day+1year== endday
        // date.setTime(date.getTime()+1000*60*60*24*365);
        date.time=date.time
        calendar.monthIndicatorVisible=false
        calendar.showOtherDates=0
        calendar.selectionMode=1
        calView()
    }


    fun calView(){
//        calendar.setOnDateChangeListener({ calendarView: CalendarView, i1: Int, i2: Int, i3: Int ->
//            date.year=i1-1900
//            date.month=i2
//            date.date=i3
//
//            var tvTitleDate=binding.tvTitleDate
//            tvTitleDate.text=sdf.format(date)
//
//
//        })
    }//clView

    //to hide fab
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

}