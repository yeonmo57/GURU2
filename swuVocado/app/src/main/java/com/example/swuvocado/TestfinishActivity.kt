package com.example.swuvocado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

//시험 끝난 후 화면
class TestfinishActivity: AppCompatActivity() {

    //사용할 id 선언
    lateinit var menuBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testfinish)

        //사용할 id 연결
        menuBtn = findViewById(R.id.menuBtn)

        //버튼이 눌리면 MenuActivity로 넘어가기
        menuBtn.setOnClickListener {
            var intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}