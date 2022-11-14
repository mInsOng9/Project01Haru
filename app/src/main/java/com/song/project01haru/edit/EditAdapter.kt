package com.song.project01haru.edit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class EditAdapter(fragmentActivity: FragmentActivity,var frag:String) : FragmentStateAdapter(fragmentActivity) {

    var fragments = mutableListOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        return fragments[position]!!
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    init{
        if(frag.equals("expinc")) {
            fragments= mutableListOf<Fragment>(EditIncFragment(),EditExpFragment())
        }
        if(frag.equals("todo")){
            fragments= mutableListOf<Fragment>( EditTodoFragment())
        }
        if(frag.equals("skd")){
            fragments= mutableListOf<Fragment>(EditSkdFragment(),EditEventFragment())
        }
    }

}