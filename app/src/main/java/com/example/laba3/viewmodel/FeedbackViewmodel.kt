package com.example.laba3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.laba3.entity.Feedback
import com.example.laba3.repository.FeedbackRepository
import com.example.laba3.ui.Result

data class uiState(
    val trash: String
) {}

class FeedbackViewmodel(private val repository: FeedbackRepository): ViewModel() {
    private val feedbacks = MutableLiveData<Result<List<Feedback>>>()

    suspend fun getFeedback(): LiveData<Result<List<Feedback>>> {
        try {
            val data = repository.getAllFeedback()
            feedbacks.value = Result.Success(data)
        } catch (e: Exception) {
            feedbacks.value = Result.Error(e)
        }
        return feedbacks
    }

    suspend fun updateFeedbackList() {

    }
}