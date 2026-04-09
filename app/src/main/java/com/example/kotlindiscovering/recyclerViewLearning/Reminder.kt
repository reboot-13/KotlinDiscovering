package com.example.kotlindiscovering.recyclerViewLearning

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Reminder (
    var id: Long,
    val title: String,
    val description: String,
    val isDone: Boolean = false,
    val isEditing: Boolean = false
)