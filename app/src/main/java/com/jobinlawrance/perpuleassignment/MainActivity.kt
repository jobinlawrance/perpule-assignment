package com.jobinlawrance.perpuleassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jobinlawrance.perpuleassignment.ui.audiolist.view.AudioListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list_button.setOnClickListener {
            val intent = Intent(this,AudioListActivity::class.java)
            startActivity(intent)
        }
    }
}
