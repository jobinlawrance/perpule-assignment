package com.jobinlawrance.perpuleassignment.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio")
data class Audio(
    @PrimaryKey
    @ColumnInfo(name = "audioId")
    val id: String,
    val desc: String,
    val audioUrl: String,
    val audioPath: String
)