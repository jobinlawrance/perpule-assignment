package com.jobinlawrance.perpuleassignment.ui.audiolist.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jobinlawrance.perpuleassignment.R
import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioDetailActivity
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_audio_list.*
import javax.inject.Inject

class AudioListActivity : AppCompatActivity(), AudioListContract.View {
    lateinit var audioListAdapter: AudioListAdapter

    @Inject
    lateinit var audioListPresenter: AudioListContract.Presenter

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_list)
        initialize()
        loadList()
    }

    private fun initialize() {
        audioListAdapter = AudioListAdapter(this::onAudioItemClickListener)
        recycler_view.adapter = audioListAdapter
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    @SuppressLint("CheckResult")
    private fun loadList() {
        audioListPresenter
            .getAudioList()
            .applySchedulers()
            .autoDisposable(scopeProvider)
            .subscribe(this::renderView)
    }

    override fun renderView(audioListViewState: AudioListViewState) = when (audioListViewState) {
        AudioListViewState.Loading -> progress_bar.isVisible = true
        is AudioListViewState.Success -> {
            progress_bar.isGone = true
            audioListAdapter.addAudioList(audioListViewState.audioDataList)
        }
        is AudioListViewState.Error -> TODO()
    }

    private fun onAudioItemClickListener(audioData: AudioData) {
        startActivity(AudioDetailActivity.getIntent(this, audioData))
    }
}
