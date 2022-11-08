package com.song.project01haru.main.skd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.song.project01haru.databinding.FragmentSchdBinding

class SkdFragment :Fragment() {

    lateinit var binding:FragmentSchdBinding
    var items:MutableList<SkdItem> = mutableListOf()
    val recycler by lazy { binding.recycler }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSchdBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items.add(SkdItem("24 MON","Holiday","Eventttt","Hang out","w/ friendssss","20:00"))
        items.add(SkdItem("24 MON","Holiday","Eventttt","Hang out","w/ friendssss","20:00"))
        items.add(SkdItem("24 MON","Holiday","Eventttt","Hang out","w/ friendssss","20:00"))

        recycler.adapter= SkdAdapter(requireActivity(),items)
    }
}