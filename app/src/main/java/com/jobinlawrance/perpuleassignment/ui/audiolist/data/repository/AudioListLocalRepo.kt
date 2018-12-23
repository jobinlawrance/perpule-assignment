package com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository

import android.util.Log
import com.jobinlawrance.perpuleassignment.ApiResponse
import com.jobinlawrance.perpuleassignment.persistance.Audio
import com.jobinlawrance.perpuleassignment.persistance.AudioDao
import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import io.reactivex.Observable
import javax.inject.Inject

class AudioListLocalRepo @Inject constructor(private val audioDao: AudioDao): AudioListContract.Repository {
    override fun getAudioList(): Observable<List<AudioData>> =
        audioDao
            .getAudios()
            .filter { it.isNotEmpty() }
            .map { list -> list.map { AudioData(it.audioPath,it.desc,it.id) } }

    fun updateLocalRepo(audioDataList: List<AudioData>) {
        audioDao.insertAudio(audioDataList.map { Audio(it.itemId,it.desc, it.audio, "") })
    }
}