package com.jobinlawrance.perpuleassignment.ui.audiodetail.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jobinlawrance.perpuleassignment.R
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import dagger.android.AndroidInjection
import javax.inject.Inject

class AudioDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var audioServiceInterator: AudioServiceInterator

    val downloadServiceObservable by lazy {
        audioServiceInterator.getService()
            .replay()
            .refCount()
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_detail)
        downloadServiceObservable
            .flatMap { it.playAudio("") }
            .applySchedulers()
            .subscribe({
                Log.d("###Perpule",it.toString())
            },{
                Log.d("###Perpule",it.message,it)
            })
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent = Intent(context, AudioDetailActivity::class.java)
    }
}
