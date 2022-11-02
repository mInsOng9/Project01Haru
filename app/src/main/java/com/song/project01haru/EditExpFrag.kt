package com.song.project01haru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.song.project01haru.databinding.ActivityEditBinding
import com.song.project01haru.databinding.ActivityEditExpFragBinding

class EditExpFrag : Fragment() {

    lateinit var binding: ActivityEditExpFragBinding
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?) : View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.activity_edit_exp_frag, container, false)
    }
    //위 onCreateView()메소드를 통해 만들어진 뷰 안에 값들을 제어하기 위해 자동으로 실행되는 메소드
    //View가 만들어진 후 자동으로 실행되는 메소드
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        tv = view.findViewById(R.id.tv)
//        btn = view.findViewById(R.id.btn)
//        btn.setOnClickListener(View.OnClickListener { tv.setText("Nice to meet you") })
//    }

}