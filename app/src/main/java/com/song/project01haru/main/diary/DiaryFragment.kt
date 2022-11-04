package com.song.project01haru.main.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.song.project01haru.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment(){

    lateinit var binding:FragmentDiaryBinding
    var items:MutableList<DiaryItem> = mutableListOf()

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
        super.onViewCreated(view, savedInstanceState)

        items.add(DiaryItem("24 MON","","dklfajkld"))
        items.add(DiaryItem("24 MON","","dklfajkld"))
        items.add(DiaryItem("24 MON","","dklfajkld"))

        binding.recycler.adapter= DiaryAdapter(requireActivity(),items)
    }
}