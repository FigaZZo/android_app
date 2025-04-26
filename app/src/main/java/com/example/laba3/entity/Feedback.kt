package com.example.laba3.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback_table")
data class Feedback(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val text: String,
    val design: Int,
    val functionality: Int
) {}