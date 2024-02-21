package com.example.kotlindiscovering.model
import androidx.core.graphics.drawable.toDrawable
import androidx.transition.R
import java.io.ByteArrayOutputStream
import java.util.Collections

typealias FoodListListener = (foodList: List<Food>) -> Unit

class FoodService {
    private var foodList: MutableList<Food> = mutableListOf()
    private val listeners = mutableSetOf<FoodListListener>()
    private val rollList = Rolls().FOOD_LIST

    init {
        for ((id) in rollList.withIndex()) {
            foodList.add(
                Food(
                    name = rollList[id][0].toString(),
                    cost = rollList[id][1].toString() + " р",
                    image = rollList[id][2].toString().toInt()
                )
            )
        }
    }

    fun getFood(): List<Food>{
        return foodList
    }


    fun addListener(listener: FoodListListener){
        listeners.add (listener)
        listener.invoke(foodList)
    }

    fun removeListener(listener: FoodListListener){
        listeners.remove (listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(foodList) }
    }
}