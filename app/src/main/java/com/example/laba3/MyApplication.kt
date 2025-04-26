package com.example.laba3

import android.app.Application
import com.example.laba3.database.FeedbackDatabase
import com.example.laba3.repository.FeedbackRepository

class MyApplication: Application() {
    private val database by lazy { FeedbackDatabase.getInstance(this) }
    val feedbackRepository by lazy { FeedbackRepository(database.feedbackDao()) }
}