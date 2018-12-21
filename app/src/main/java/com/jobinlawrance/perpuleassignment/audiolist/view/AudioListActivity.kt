package com.jobinlawrance.perpuleassignment.audiolist.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.jobinlawrance.perpuleassignment.R
import com.jobinlawrance.perpuleassignment.audiolist.AudioListContract
import kotlinx.android.synthetic.main.activity_audio_list.*

class AudioListActivity : AppCompatActivity(), AudioListContract.View {
    lateinit var audioListAdapter: AudioListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_list)
        initialize()
    }

    private fun initialize() {
        audioListAdapter = AudioListAdapter(this::onAudioItemClickListener)
    }

    override fun renderView(audioListViewState: AudioListViewState) = when(audioListViewState) {
        AudioListViewState.Loading -> progress_bar.isVisible = true
        is AudioListViewState.Success -> {
            progress_bar.isGone = true
            audioListAdapter.addAudioList(audioListViewState.audioDataList)
        }
        is AudioListViewState.Error -> TODO()
    }

    fun onAudioItemClickListener(itemId: String) {
        Log.w("###Perpule","item id is $itemId")
    }
}
