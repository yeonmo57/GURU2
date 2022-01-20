package com.example.swuvocado

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

//단어추가 화면
class AddvocaActivity : AppCompatActivity() {
    //사용할 id 선언
    lateinit var myHelper : myDBHelper
    lateinit var edtWord : EditText
    lateinit var btnDel : Button
    lateinit var edtMean : EditText
    lateinit var btnInsert : Button
    lateinit var edtWordResult : EditText
    lateinit var edtMeanResult : EditText
    lateinit var btnMenu : Button
    lateinit var btnInit : Button
    lateinit var btnSelect : Button
    lateinit var sqlDB : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addvoca)
        //사용할 id 연결
        edtWord = findViewById(R.id.edtWord)
        btnDel = findViewById(R.id.btnDel)
        edtMean = findViewById(R.id.edtMean)
        btnInsert = findViewById(R.id.btnInsert)
        edtWordResult = findViewById(R.id.edtWordResult)
        edtMeanResult = findViewById(R.id.edtMeanResult)
        btnMenu = findViewById(R.id.btnMenu)
        btnInit = findViewById(R.id.btnInit)
        btnSelect = findViewById(R.id.btnSelect)

        myHelper = myDBHelper(this)
        //삭제 버튼 클릭 시, 단어와 동일한 row 삭제
        btnDel.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            sqlDB.execSQL(" DELETE FROM wordTBL WHERE WORD = '" +
                    edtWord.text.toString()+"';")

            sqlDB.close()

            btnSelect.callOnClick()
        }
        //입력 버튼 클릭 시 입력 값 테이블에 작성
        btnInsert.setOnClickListener {

            sqlDB = myHelper.writableDatabase

            //입력 값이 null이 아닌 경우
            if(edtWord.length() != 0 && edtMean.length() != 0) {
                //예외처리
                try {
                    sqlDB.execSQL(
                        // sql문을 이용한 row 삽입
                        "INSERT INTO wordTBL VALUES ('"
                                + edtWord.text.toString() + "' ,'"
                                + edtMean.text.toString() + "');"
                    )
                    Toast.makeText(applicationContext, "입력됨", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) { //오류일 경우
                    Toast.makeText(applicationContext, "입력 오류입니다. 다시 확인해주세요.", Toast.LENGTH_SHORT)
                        .show()
                }
                sqlDB.close()


                btnSelect.callOnClick()
            } else { //입력 값들 중 null 값이 있을 경우
                Toast.makeText(applicationContext,"빈 칸을 모두 채워주세요.",Toast.LENGTH_SHORT).show()
            }
        }
        //메뉴 버튼 클릭 시, MenuActivity로 이동
        btnMenu.setOnClickListener {
            var intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        //초기화 버튼 클릭 시, 테이블 초기화
        btnInit.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            myHelper.onUpgrade(sqlDB, 1,2)
            sqlDB.close()

            Toast.makeText(applicationContext, "초기화됨", Toast.LENGTH_SHORT).show()
            btnSelect.callOnClick()
        }
        //조회 버튼 클릭 시, 테이블 내용 조회
        btnSelect.setOnClickListener {
            sqlDB = myHelper.readableDatabase
            var cursor : Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM wordTBL;", null)

            var strWords = "단어" + "\r\n" + "--------" + "\r\n"
            var strMeans = "뜻" + "\r\n" + "--------" + "\r\n"

            while(cursor.moveToNext()) {
                strWords += cursor.getString(0) + "\r\n"
                strMeans += cursor.getString(1) + "\r\n"
            }
            edtWordResult.setText(strWords)
            edtMeanResult.setText(strMeans)

            cursor.close()
            sqlDB.close()
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