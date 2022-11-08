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
    lateinit var item : ExpIncItem
    lateinit var day:String

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
        day = arguments?.getString("date").toString()

        item=ExpIncItem()

        item.day=day
        item.mosInc="20,000"
        item.mosExp="10,000"
        item.mosTotal="10,000"
        item.account= R.drawable.type_card
        item.type="WOORI"
        item.category="HObby"
        item.note="hihi"
        item.amount="+20,000"
        items.add(item)
        binding.recycler.adapter= ExpIncAdapter(requireActivity(),items)
    }
}