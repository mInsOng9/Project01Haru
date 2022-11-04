package com.song.project01haru.main.expinc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.song.project01haru.R
import com.song.project01haru.databinding.FragmentExpIncBinding

class ExpIncFragment:Fragment() {

    lateinit var binding:FragmentExpIncBinding
    var items = mutableListOf<ExpIncItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentExpIncBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items.add(
            ExpIncItem("24 MON","20,000","10,000","10,000",
                R.drawable.type_cash,"","Hobby","hihih","+20,000")
        )
        items.add(
            ExpIncItem("24 MON","20,000","10,000","10,000",
                R.drawable.type_card,"NH","Hobby","hihih","+20,000")
        )
        items.add(
            ExpIncItem("24 MON","20,000","10,000","10,000",
                R.drawable.type_card,"WOORI","Hobby","hihih","+20,000")
        )
        binding.recycler.adapter= ExpIncAdapter(requireActivity(),items)
    }
}