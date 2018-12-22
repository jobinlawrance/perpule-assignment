package com.jobinlawrance.perpuleassignment.audiolist.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.jobinlawrance.perpuleassignment.R
import com.jobinlawrance.perpuleassignment.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
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

    fun onAudioItemClickListener(itemId: String) {
        Log.w("###Perpule", "item id is $itemId")
    }
}
