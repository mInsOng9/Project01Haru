package com.song.project01haru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.song.project01haru.databinding.FragmentEditIncBinding
import com.song.project01haru.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var items:MutableList<HomeItem> = mutableListOf<HomeItem>()

    //recyclerview
    val recyclerView by lazy {binding.recycler}

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //add items
        items.add(HomeItem("24 MON","hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))
        items.add(HomeItem("24 MON","hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))
        items.add(HomeItem("24 MON","hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))

        recyclerView.adapter=HomeAdapter(requireActivity(),items)
        recyclerView.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)

    }
}