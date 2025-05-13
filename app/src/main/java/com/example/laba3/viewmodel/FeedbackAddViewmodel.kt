package com.example.laba3.viewmodel

import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.laba3.repository.FeedbackRepository
import com.example.laba3.ui.Result

data class uiStateAddFeedback(
    var editText: String? = null,
    var ratingFunc: Int? = null,
    var ratingDesign: Int? = null
) {}

class FeedbackAddViewmodel(private val repository: FeedbackRepository): ViewModel() {
    private val uiState = uiStateAddFeedback()

    fun saveState(text: String, ratingFunc: Int?, ratingDesign: Int?) {
        uiState.editText = text
        uiState.ratingFunc = ratingFunc
        uiState.ratingDesign = ratingDesign
    }

    fun getText() = uiState.editText
    fun getRatingFunc() = uiState.ratingFunc
    fun getRatingDesign() = uiState.ratingDesign

    suspend fun addNote(text: String, ratingFunc: Int?, ratingDesign: Int?): Result<Unit> {
        try {
            repository.addFeedback(text, ratingFunc, ratingDesign)
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}