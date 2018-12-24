package com.jobinlawrance.perpuleassignment.ui.audiolist.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jobinlawrance.perpuleassignment.R
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioDetailActivity
import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_audio_list.*
import javax.inject.Inject
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth




class AudioListActivity : AppCompatActivity(), AudioListContract.View {
    lateinit var audioListAdapter: AudioListAdapter

    @Inject
    lateinit var audioListPresenter: AudioListContract.Presenter

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    var revealX: Int = 0
    var revealY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_list)
        if (savedInstanceState == null)
            showRevealAnimation()
        initialize()
        loadList()
    }

    private fun showRevealAnimation() {
        root_layout.isInvisible = true
        revealX = intent.getIntExtra(REVEAL_X, 0)
        revealY = intent.getIntExtra(REVEAL_Y, 0)

        val viewTreeObserver = root_layout.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    revealActivity(revealX, revealY)
                    root_layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    fun revealActivity(x: Int, y: Int) {
        val finalRadius = (Math.max(root_layout.width, root_layout.height) * 1.1).toFloat()

        // create the animator for this view (the start radius is zero)
        val circularReveal = ViewAnimationUtils.createCircularReveal(root_layout, x, y, 0f, finalRadius)
        circularReveal.duration = 400
        circularReveal.interpolator = AccelerateInterpolator()

        // make the view visible and start the animation
        root_layout.isVisible = true
        circularReveal.start()
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

        is AudioListViewState.NetworkError -> {
            val toast = Toast.makeText(this, audioListViewState.message, Toast.LENGTH_LONG)
            toast.show()
        }
        is AudioListViewState.DatabaseError -> {
            progress_bar.isGone = true
            error_text.isVisible = true
            error_text.text = audioListViewState.message
        }
    }

    private fun onAudioItemClickListener(audioData: AudioData) {
        startActivity(AudioDetailActivity.getIntent(this, audioData))
    }

    companion object {

        private const val REVEAL_X = "revealx"
        private const val REVEAL_Y = "revealy"

        @JvmStatic
        fun getIntent(context: Context, revealX: Int, revealY: Int): Intent {
            return Intent(context, AudioListActivity::class.java)
                .putExtra(REVEAL_X, revealX)
                .putExtra(REVEAL_Y, revealY)
        }
    }
}
