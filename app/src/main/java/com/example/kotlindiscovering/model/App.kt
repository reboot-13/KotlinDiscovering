package com.example.kotlindiscovering.model

import android.app.Application

class App: Application() {
    val foodService = FoodService()
}