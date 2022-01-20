package com.example.swuvocado

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//입력한 단어 출력화면
class ShowvocaActivity : AppCompatActivity() {

    // 사용할 id 선언하기
    lateinit var wordText: EditText
    lateinit var meanText: EditText
    lateinit var test : Button
    lateinit var addVoca : Button
    lateinit var main : Button
    lateinit var myHelper : AddvocaActivity.myDBHelper
    lateinit var sqlDB : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showvoca)

        //사용할 id 연결해주기
        wordText = findViewById(R.id.wordText)
        meanText = findViewById(R.id.meanText)
        test = findViewById(R.id.test)
        addVoca = findViewById(R.id.addVoca)
        main = findViewById(R.id.main)

        //DB 사용
        val myHelper = myDBHelper(this)

        //AddvocaActivity에서 만든 DB 가져오기
        sqlDB = myHelper.readableDatabase
        var cursor : Cursor = sqlDB.query("wordTBL",null,null,null,null,null,null)

        //DB 출력
        var strWords = "단어" + "\r\n" + "--------" + "\r\n"
        var strMeans = "뜻" + "\r\n" + "--------" + "\r\n"

        //입력한 전체 단어 출력
        while(cursor.moveToNext()) {
            strWords += cursor.getString(0) + "\r\n"
            strMeans += cursor.getString(1) + "\r\n"
        }
        wordText.setText(strWords)
        meanText.setText(strMeans)

        cursor.close()
        sqlDB.close()

        //시험 보기 버튼 클릭 시 TestreadyActivity로 이동
        test.setOnClickListener {
            var intent = Intent(this, TestreadyActivity::class.java)
            startActivity(intent)
        }

        //단어 입력 버튼 클릭 시 AddVocaActivity로 이동
        addVoca.setOnClickListener {
            var intent = Intent(this, AddvocaActivity::class.java)
            startActivity(intent)
        }

        //메뉴 화면 클릭 시 MenuActivity로 이동
        main.setOnClickListener {
            var intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "wordDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE wordTBL (WORD TEXT NOT NULL PRIMARY KEY, MEAN TEXT NOT NULL) ;")
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion:Int, newVersion:Int) {
            db!!.execSQL("DROP TABLE IF EXISTS wordTBL ;")
            onCreate(db)
        }
    }
}