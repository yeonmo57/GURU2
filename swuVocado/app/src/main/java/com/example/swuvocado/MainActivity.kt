package com.example.swuvocado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast


//메인 화면
public class MainActivity : AppCompatActivity() {
    //사용할 id 선언
    lateinit var name:EditText
    lateinit var startBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        //사용할 id 연결
        name = findViewById(R.id.name)
        startBtn = findViewById(R.id.startBtn)

        //start버튼을 클릭하면 입력한 이름과 함께 인사 출력
        startBtn.setOnClickListener {
            var str_name: String = name.text.toString()

            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("intent_name", str_name)
            startActivity(intent)

            Toast.makeText(applicationContext, str_name +"님 안녕하세요", Toast.LENGTH_LONG).show()

        }

    }
}