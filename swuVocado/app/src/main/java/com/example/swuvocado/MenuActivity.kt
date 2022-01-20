package com.example.swuvocado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.concurrent.atomic.DoubleAdder

//메뉴 화면
class MenuActivity : AppCompatActivity() {
    //사용할 id 선언
    lateinit var showVaca:Button
    lateinit var addVoca:Button
    lateinit var test:Button

    lateinit var str_name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //사용할 id 연결
        showVaca = findViewById(R.id.showVoca)
        addVoca =findViewById(R.id.addVoca)
        test = findViewById(R.id.test)

        val intent = intent
        str_name = intent.getStringExtra("intent_name").toString()

        //단어보기 버튼 클릭 시 ShowvocaActivity로 이동
        showVaca.setOnClickListener {
            var intent = Intent(this, ShowvocaActivity::class.java)
            startActivity(intent)
        }

        //단어추가 버튼 클릭 시 AddvocaActivity로 이동
        addVoca.setOnClickListener {
            var intent = Intent(this, AddvocaActivity::class.java)
            startActivity(intent)
        }

        //시험 시작 버튼 클릭 시 TestreadyActivity로 이동
        test.setOnClickListener {
            var intent = Intent(this, TestreadyActivity::class.java)
            startActivity(intent)
        }
    }
}


