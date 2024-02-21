package com.example.kotlindiscovering.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

open class Food(
    val name: String,
    val cost: String,
    val image: Int,
//    val category: Category
)