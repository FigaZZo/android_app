package com.example.laba3.ui

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    class Empty : Result<Nothing>()
}