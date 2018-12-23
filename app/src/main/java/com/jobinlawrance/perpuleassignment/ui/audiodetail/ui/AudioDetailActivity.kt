package com.jobinlawrance.perpuleassignment.ui.audiodetail.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jobinlawrance.perpuleassignment.MainActivity
import com.jobinlawrance.perpuleassignment.R
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import com.jobinlawrance.perpuleassignment.ui.audiodetail.AudioDetailContract
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import dagger.android.AndroidInjection
import javax.inject.Inject

class AudioDetailActivity : AppCompatActivity(), AudioDetailFragment.OnFragmentInteractionListener,
    AudioDetailContract.View {

    lateinit var audioData: AudioData

    @Inject
    lateinit var presenter: AudioDetailContract.Presenter

    var fragment: AudioDetailFragment? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_detail)
        audioData = intent.getParcelableExtra(ITEM_ID)
        setFragmentForAudio(audioData)
        presenter
            .playAudio(audioData.itemId, false)
            .applySchedulers()
            .subscribe(this::renderView) {
                Log.e("###Perpule",it.message,it)
            }

    }

    override fun renderView(audioViewState: AudioViewState) {
        when (audioViewState) {
            is AudioViewState.ShowNext -> {
                this.audioData = audioViewState.audioData
                setFragmentForAudio(audioViewState.audioData)
            }
            is AudioViewState.AudioState -> {
                fragment?.showLoadingState(audioViewState.isLoading)
            }
            AudioViewState.LaunchWelcome -> {
                val intent = Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
            is AudioViewState.Error -> {
                TODO()
            }
        }
    }


    private fun setFragmentForAudio(audioData: AudioData) {
        fragment = AudioDetailFragment.newInstance(audioData.desc)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment!!)
        transaction.commit()
    }

    @SuppressLint("CheckResult")
    override fun onClickNext() {
        presenter
            .playAudio(audioData.itemId, true)
            .subscribe()
    }

    companion object {
        const val ITEM_ID = "itemid"

        @JvmStatic
        fun getIntent(context: Context, audioData: AudioData): Intent =
            Intent(context, AudioDetailActivity::class.java)
                .putExtra(ITEM_ID, audioData)
    }
}
