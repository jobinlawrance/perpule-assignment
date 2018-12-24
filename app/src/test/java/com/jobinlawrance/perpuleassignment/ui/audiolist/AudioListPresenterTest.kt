package com.jobinlawrance.perpuleassignment.ui.audiolist

import com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository.AudioListLocalRepo
import com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository.AudioListNetworkRepo
import com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository.AudioListPartialChange
import com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository.AudioListRepo
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class AudioListPresenterTest {

    lateinit var localRepo: AudioListLocalRepo
    lateinit var networkRepo: AudioListNetworkRepo
    lateinit var repo: AudioListContract.Repository
    lateinit var presenter: AudioListContract.Presenter

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUp() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
        }
    }

    @Before
    fun beforeEachTest() {
        localRepo = mock()
        networkRepo = mock()
        repo = AudioListRepo(networkRepo, localRepo)
        presenter = AudioListPresenter(repo)
    }

    @Test
    fun network_result_updates_local_database() {
        val listOfAudioData = listOf(
            AudioData("https://asf", "blah", "3"),
            AudioData("https://asf", "blah", "4")
        )
        whenever(networkRepo.getAudioList())
            .thenReturn(
                Observable
                    .just(
                        AudioListPartialChange
                            .AudioList(listOfAudioData)
                    )
            )

        whenever(localRepo.getAudioList())
            .thenReturn(Observable.just(AudioListPartialChange.AudioList(emptyList())))

        presenter
            .getAudioList()
            .subscribe()

        argumentCaptor<List<AudioData>>().apply {
            verify(localRepo).updateLocalRepo(capture())

            assertEquals(firstValue, listOfAudioData)
        }
    }

    @Test
    fun ensure_empty_database_doesnt_trigger_ui_update() {

        whenever(localRepo.getAudioList()).thenReturn(Observable.just(AudioListPartialChange.AudioList(emptyList())))
        whenever(networkRepo.getAudioList()).thenReturn(Observable.just(AudioListPartialChange.AudioList(emptyList())))

        presenter
            .getAudioList()
            .test()
            .assertNoValues()

    }

}