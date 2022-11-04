package com.song.project01haru.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.song.project01haru.databinding.FragmentEditIncBinding

class EditIncFragment : Fragment() {

    lateinit var binding: FragmentEditIncBinding
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
//        return inflater.inflate(R.layout.activity_edit_inc_fragment, container, false)
        binding = FragmentEditIncBinding.inflate(inflater, container, false)
        return binding.root
    }
    //위 onCreateView()메소드를 통해 만들어진 뷰 안에 값들을 제어하기 위해 자동으로 실행되는 메소드
    //View가 만들어진 후 자동으로 실행되는 메소드
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding=ActivityEditIncFragmentBinding.bind(view)
//
//    }
}