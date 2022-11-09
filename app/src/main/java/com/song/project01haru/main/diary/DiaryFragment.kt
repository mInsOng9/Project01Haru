package com.song.project01haru.main.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.song.project01haru.databinding.FragmentDiaryBinding
import com.song.project01haru.main.home.HomeAdapter
import com.song.project01haru.main.home.HomeItem

class DiaryFragment : Fragment(){

    lateinit var binding:FragmentDiaryBinding
    var items:MutableList<DiaryItem> = mutableListOf()
    val recyclerView by lazy {binding.recycler}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDiaryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView.adapter= DiaryAdapter(requireActivity(),items)
    }
    fun changeDay(day:String){
        items.clear()
        items.add(DiaryItem(day,"","dklfajkld"))
        binding.recycler.adapter= DiaryAdapter(requireActivity(),items)    }

    fun changeDays(days:MutableList<String>){
        items.clear()
        days.forEach{ day->
            items.add(DiaryItem(day,"","dklfajkld"))
        }
        binding.recycler.adapter= DiaryAdapter(requireActivity(),items)
    }
}