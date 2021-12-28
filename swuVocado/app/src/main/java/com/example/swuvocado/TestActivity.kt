package com.example.swuvocado

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer

//시험 화면
class TestActivity : AppCompatActivity() {

    //사용할 id 선언
    private var time = 1500;
    private var timerTask : Timer? = null

    lateinit var secTextView: TextView
    lateinit var milliTextView: TextView

    lateinit var myHelper: AddvocaActivity.myDBHelper
    lateinit var sqlDB: SQLiteDatabase

    lateinit var meanTextView : TextView
    lateinit var testButton: Button
    lateinit var answerText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //사용할 id 연결
        secTextView = findViewById(R.id.secTextView)
        milliTextView = findViewById(R.id.milliTextView)

        meanTextView = findViewById(R.id.meanTextView)
        testButton = findViewById(R.id.testButton)
        answerText = findViewById(R.id.answerText)

        //DB 사용
        val myHelper = myDBTestHelper(this)

        //AddvocaActivity에서 만든 DB 랜덤으로 가져오기
        sqlDB = myHelper.readableDatabase

        var cursor: Cursor = sqlDB.rawQuery("SELECT * FROM wordTBL ORDER BY RANDOM();",null)
        cursor.moveToFirst()

        //입력받은 단어의 뜻 출력
        var strMean = "\t\n" + cursor.getString(1) + "\t\n"

        meanTextView.setText(strMean)
        meanTextView.textSize = 50f

        // 타이머 작동 시작
        start()

        //버튼이 눌리면 정답확인
        testButton.setOnClickListener {
            var str_answer: String = answerText.text.toString()
                if (cursor.getString(0).equals(str_answer)) {
                    val toast = Toast.makeText(this, cursor.getString(0)
                            +" Correct!", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()

                }
                //오답일 경우, 정답 출력
                else {
                    val toast = Toast.makeText(this, "Wrong! " +
                            cursor.getString(0), Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            cursor.moveToNext()

            //해당 테이블의 마지막 행까지 출력하고 타이머 리셋, 시작
            if(cursor.getPosition() < cursor.getCount()) {
                meanTextView.setText(cursor.getString(1))
                meanTextView.textSize = 50f
                reset()
                start()
            }
            //해당 테이블의 저장된 모든 행의 출력이 끝난 경우, TestfinishActivity로 이동
            else if(cursor.getPosition() >= cursor.getCount()) {
                var intent = Intent(this, TestfinishActivity::class.java)
                startActivity(intent)
                cursor.close()
                sqlDB.close()
                myHelper.close()
            }

        }
    }

    private fun start() {       //0.01초마다 time 감소시키면서 UI갱신
        timerTask = timer(period = 10) {
            time--
            val sec = time / 100
            val milli = time % 100

            if (sec == 0 && milli == 0) {
                timerTask?.cancel()
            }
            runOnUiThread {
                    secTextView.text = "$sec"
                    milliTextView.text = "$milli"
            }
            // 15초 안에 입력하지 않았을 경우, 토스트 창 띄우기
            if (sec == 0 && milli == 0) {
                runOnUiThread {
                    val toast =
                        Toast.makeText(this@TestActivity, "15초 안에 입력하세요.", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }




        }
    }

    // 타이머 시간 초기화(15초부터 다시 카운트다운)
    private fun reset() {
        timerTask?.cancel()

        time = 1500
        secTextView.text = "0"
        milliTextView.text = "00"

    }


    inner class myDBTestHelper(context: Context) : SQLiteOpenHelper(context, "wordDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE wordTBL (WORD CHAR(30) PRIMARY KEY, MEAN CHAR(30)) ;")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS wordTBL ;")
            onCreate(db)
        }
    }
}
