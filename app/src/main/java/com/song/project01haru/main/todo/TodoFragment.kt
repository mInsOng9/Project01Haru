package com.song.project01haru.main.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.song.project01haru.databinding.FragmentTodoBinding
import com.song.project01haru.main.todo.TodoAdapter
import com.song.project01haru.main.todo.TodoItem

class TodoFragment : Fragment() {

    lateinit var binding:FragmentTodoBinding
    var items:MutableList<TodoItem> = mutableListOf()
    val recyclerView by lazy{binding.recycler}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        items.add(TodoItem("24 MON","sleeppppppp"))
        items.add(TodoItem("24 MON","sleeppppppp"))
        items.add(TodoItem("24 MON","sleeppppppp"))
        recyclerView.adapter= TodoAdapter(requireActivity(),items)
    }
}