package com.example.laba3.repository

import com.example.laba3.dao.FeedbackDao
import com.example.laba3.entity.Feedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedbackRepository(private val dao: FeedbackDao) {
    suspend fun getAllFeedback(): List<Feedback> {
        return withContext(Dispatchers.IO) {
            dao.getAllFeedback()
        }
    }

    suspend fun addFeedback(feedback: Feedback) {
        withContext(Dispatchers.IO) {
            dao.addFeedback(feedback)
        }
    }

    suspend fun addFeedback(text: String, functionality: Int?, design: Int?) {
        withContext(Dispatchers.IO) {
            dao.addFeedback(Feedback(text = text,
                design = (design ?: 5),
                functionality = (functionality ?: 5)
            ))
        }
    }

    suspend fun filterByRating(functionality: Int?, design: Int?): List<Feedback> {
        return withContext(Dispatchers.IO) {
            if (functionality == null && design == null) {
                dao.getAllFeedback()
            } else if (functionality == null) {
                dao.filterByDesign(design!!)
            } else if (design == null) {
                dao.filterByFunctionality(functionality)
            } else {
                dao.filterByDesignAndFunctionality(design, functionality)
            }
        }
    }
}