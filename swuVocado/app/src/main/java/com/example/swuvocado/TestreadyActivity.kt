package com.example.swuvocado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

//시험 시작 전 화면
class TestreadyActivity: AppCompatActivity() {

    //사용할 id 선언하기
    lateinit var test: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testready)

        //사용할 id 연결해주기
        test = findViewById(R.id.test)

        //메뉴로 돌아가기 버튼이 눌리면 TestActivity로 이동
        test.setOnClickListener {
            var intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }
}