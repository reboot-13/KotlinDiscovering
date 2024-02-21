package com.example.kotlindiscovering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlindiscovering.database.FoodDatabase
import com.example.kotlindiscovering.databinding.ActivityMainBinding
import com.example.kotlindiscovering.databinding.ItemFoodBinding
import com.example.kotlindiscovering.model.App
import com.example.kotlindiscovering.model.Food
import com.example.kotlindiscovering.model.FoodActionListener
import com.example.kotlindiscovering.model.FoodAdapter
import com.example.kotlindiscovering.model.FoodListListener
import com.example.kotlindiscovering.model.FoodService



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: FoodAdapter
    lateinit var foodToCart : Food


    private val foodService: FoodService
        get() = (applicationContext as App).foodService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodToCart = savedInstanceState?.getParcelable(KEY_FOOD_TO_CART) ?: Food("asd", "asdas", 123 )

        adapter = FoodAdapter(object: FoodActionListener{
            override fun onFoodDetails(food: Food) {
                Toast.makeText(this@MainActivity, food.name, Toast.LENGTH_SHORT).show()
            }

            override fun onFoodAddToCart(food: Food) {
                showCart()
            }

        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRolls.layoutManager = layoutManager
        binding.recyclerViewRolls.adapter = adapter
        foodService.addListener(foodListener)

        binding.cartButton.setOnClickListener(){onCartPressed()}
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        foodService.removeListener(foodListener)
    }
    private val foodListener: FoodListListener = {
        adapter.foodList = it
    }

    fun showCart() {
        binding.cartButton.isVisible = true
        with(binding.cartValue) {
            isVisible = true
        }
    }

    private fun onCartPressed(){
        val intent = Intent(this, CartActivity::class.java)
       // intent.putParcelableArrayListExtra(CartActivity.EXTRA_FOOD_TO_CART,(( "Филадельфия", "200", 2))
        startActivity(intent)
    }

    private companion object {
        const val KEY_FOOD_TO_CART = "FOOD_TO_CART"
    }

}