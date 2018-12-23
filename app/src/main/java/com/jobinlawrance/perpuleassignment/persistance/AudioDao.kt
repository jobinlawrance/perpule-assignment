package com.jobinlawrance.perpuleassignment.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface AudioDao {

    @Query("SELECT * FROM Audio WHERE audioId = :id")
    fun getAudioById(id: String): Observable<Audio>

    @Query("SELECT * FROM Audio")
    fun getAudios(): Observable<List<Audio>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAudio(audios: List<Audio>)
}