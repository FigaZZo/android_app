package com.example.laba3.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.laba3.dao.FeedbackDao
import com.example.laba3.entity.Feedback

@Database(version = 1, entities = [Feedback::class], exportSchema = false)
abstract class FeedbackDatabase: RoomDatabase() {
    companion object {
        private lateinit var instance: FeedbackDatabase
        fun getInstance(application: Application): FeedbackDatabase {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        application,
                        FeedbackDatabase::class.java,
                        "feedback_database.db"
                    )
//                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }

    abstract fun feedbackDao(): FeedbackDao
}