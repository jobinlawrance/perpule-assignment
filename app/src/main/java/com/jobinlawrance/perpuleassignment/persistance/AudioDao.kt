package com.jobinlawrance.perpuleassignment.persistance

import androidx.room.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AudioDao {

    @Query("SELECT * FROM Audio WHERE audioId = :id")
    fun getAudioById(id: String): Single<Audio>

    @Query("SELECT * FROM Audio WHERE audioId > :id LIMIT 1")
    fun getNextAudio(id: String): Single<Audio>

    @Query("SELECT * FROM Audio")
    fun getAudios(): Observable<List<Audio>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAudio(audios: List<Audio>)

    @Update
    fun setAudioPath(audio: Audio): Single<Int>
}