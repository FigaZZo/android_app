package com.example.laba3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laba3.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class DefinitonViewModel: ViewModel() {
    private val _definition = MutableLiveData<String>()
    val definition: LiveData<String> = _definition

    fun searchWord(word: String) {
        viewModelScope.launch {
            val response = RetrofitClient.apiService.getDefinition(word)
            if (response.isSuccessful) {
                val result = response.body()
                _definition.value = result?.definition ?: "No definition found."
            } else {
                _definition.value = "Error: ${response.code()}"
            }
        }
    }
}