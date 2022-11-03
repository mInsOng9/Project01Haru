package com.song.project01haru

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.song.project01haru.databinding.ActivityMainBinding
import com.song.project01haru.databinding.DrawerHeaderBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var drawerToggle:ActionBarDrawerToggle
    var items:MutableList<HomeItem> = mutableListOf<HomeItem>()

    //recyclerview
    val recyclerView by lazy {binding.recycler}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar
        val toolbar: androidx.appcompat.widget.Toolbar =binding.toolbar
        setSupportActionBar(toolbar)

        //drawerlayout
        val drawerLayout:DrawerLayout=binding.drawerLayout
        val nav:NavigationView=binding.nav
        drawerToggle= ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close)
        drawerToggle.syncState()
        drawerToggle.drawerArrowDrawable

        //drawer haeder listener
        DrawerHeaderBinding.inflate(layoutInflater).headerProfile.setOnClickListener({startActivity(Intent(this,ProfileActivity::class.java))})

        //navigation item listener
        nav.setNavigationItemSelectedListener (NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.menu_dnav_notif-> startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS))
                R.id.menu_dnav_update-> {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(
                            "https://play.google.com/store/apps/details?id=com.example.android")
                        setPackage("com.android.vending")
                    }
                    startActivity(intent)
                }
                R.id.menu_dnav_ctc->startActivity(Intent(this,ContactUsActivity::class.java))
                else -> {
                    Toast.makeText(this@MainActivity, "else", Toast.LENGTH_SHORT).show()}
            }
            drawerLayout.closeDrawer(nav)
            false
        })

        //add items
        items.add(HomeItem("24 MON","hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))
        items.add(HomeItem("24 MON","hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))
        items.add(HomeItem("24 MON","hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))

        recyclerView.adapter=HomeAdapter(this,items)
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        //floating action button
        binding.fab.setOnClickListener {
            var intent= Intent(this,EditActivity::class.java)
            startActivity(intent)
        }

    }
    //override fun onCreateOptionsMenu(menu:Menu):Boolean {
     //   menuInflater.inflate(R.menu.item,menu)
      //  return super.onCreateOptionsMenu(menu)
    //}
}