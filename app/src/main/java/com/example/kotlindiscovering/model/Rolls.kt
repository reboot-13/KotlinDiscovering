package com.example.kotlindiscovering.model

//enum class Category(name: String) {
//    ROLLS("Роллы"),
//    SUSHI("Cуши")
//}

class Rolls {
    val FOOD_LIST = arrayOf(
        mutableListOf("Филадельфия", "680", com.example.kotlindiscovering.R.drawable.fila),
        mutableListOf("Калифорния", "590", com.example.kotlindiscovering.R.drawable.california),
        mutableListOf("Ролл с угрем", "720", com.example.kotlindiscovering.R.drawable.filaugor),
        mutableListOf("Запеченная филадельфия", "650", com.example.kotlindiscovering.R.drawable.hotfila))
}

//    val FOOD_LIST = listOf(
//        Food(0,"Филадельфия", "680", com.example.kotlindiscovering.R.drawable.fila)
//    )