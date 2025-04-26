package com.example.laba3.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.laba3.entity.Feedback

@Dao
interface FeedbackDao {
    @Query("select * from feedback_table")
    suspend fun getAllFeedback(): List<Feedback>

    @Insert
    suspend fun addFeedback(feedback: Feedback)

    @Query("select * from feedback_table " +
            "where design=:design")
    suspend fun filterByDesign(design: Int): List<Feedback>

    @Query("select * from feedback_table " +
            "where functionality=:functionality")
    suspend fun filterByFunctionality(functionality: Int): List<Feedback>

    @Query("select * from feedback_table " +
            "where design=:design AND functionality=:functionality")
    suspend fun filterByDesignAndFunctionality(design: Int, functionality: Int): List<Feedback>
}