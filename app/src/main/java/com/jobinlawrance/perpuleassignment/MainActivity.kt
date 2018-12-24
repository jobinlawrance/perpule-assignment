package com.jobinlawrance.perpuleassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jobinlawrance.perpuleassignment.ui.audiolist.view.AudioListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list_button.setOnClickListener {
            val revealX = ((it.x + it.measuredWidth) / 2).toInt()
            val revealY = ((it.y + it.measuredHeight) / 2).toInt()
            val intent = AudioListActivity.getIntent(this, 540, 150)
            startActivity(intent)
        }
    }
}
