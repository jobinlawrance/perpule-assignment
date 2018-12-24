package com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository

import com.jobinlawrance.perpuleassignment.persistance.Audio
import com.jobinlawrance.perpuleassignment.persistance.AudioDao
import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import io.reactivex.Observable
import javax.inject.Inject

class AudioListLocalRepo @Inject constructor(private val audioDao: AudioDao) : AudioListContract.Repository {
    override fun getAudioList(): Observable<AudioListPartialChange> =
        audioDao
            .getAudios()
            .filter { it.isNotEmpty() }
            .map { list -> list.map { AudioData(it.audioPath, it.desc, it.id) } }
            .map { AudioListPartialChange.AudioList(it) }

    fun updateLocalRepo(audioDataList: List<AudioData>) {
        audioDao.insertAudio(audioDataList.map { Audio(it.itemId, it.desc, it.audio, "") })
    }
}