package com.song.project01haru.edit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class EditAdapter(fragmentActivity: FragmentActivity,var frag:String) : FragmentStateAdapter(fragmentActivity) {

    var fragments = arrayOfNulls<Fragment>(2)

    override fun createFragment(position: Int): Fragment {
        return fragments[position]!!
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    init{
        if(frag.equals("expinc")) {
            fragments[0] = EditExpFragment()
            fragments[1] = EditIncFragment()
        }
        if(frag.equals("todo")){
            fragments[0]= EditTodoFragment()
            fragments[1]=null
        }
        if(frag.equals("skd")){
            fragments[0]= EditSkdFragment()
            fragments[1]= EditEventFragment()
        }
    }

}