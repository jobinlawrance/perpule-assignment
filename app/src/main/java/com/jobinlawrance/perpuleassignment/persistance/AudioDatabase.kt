package com.jobinlawrance.perpuleassignment.persistance


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Audio::class], version = 1)
abstract class AudioDatabase: RoomDatabase() {
    abstract fun audioDao(): AudioDao
}