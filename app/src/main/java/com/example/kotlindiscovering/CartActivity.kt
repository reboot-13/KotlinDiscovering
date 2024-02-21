package com.example.kotlindiscovering

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlindiscovering.databinding.ActivityCartBinding
import com.example.kotlindiscovering.model.App
import com.example.kotlindiscovering.model.CartFoodAdapter
import com.example.kotlindiscovering.model.Food


class CartActivity : BaseActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var cartFoodList : List<Food>
    lateinit var adapter : CartFoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setContentView(binding.root)

       // cartFoodList = (savedInstanceState?.getParcelable<Food>(KEY_FOOD_TO_CART) ?: intent?.getParcelableExtra(EXTRA_FOOD_TO_CART))!!

        val adapter = CartFoodAdapter(cartFoodList)
        val layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = adapter
        binding.cartRecyclerView.layoutManager = layoutManager
    }

    companion object {
        const val KEY_FOOD_TO_CART = "FOOD_TO_CART"
        const val EXTRA_FOOD_TO_CART = "EXTRA_FOOD"

    }

//    @SuppressLint("SetTextI18n")    //for concatenating strings
//    override fun onStart() {
//        super.onStart()
//        val addedProductName = intent.getStringExtra("productLabel")
//        val addedProductCost = intent.getStringExtra("productCost")?.filter{it.isDigit()}?.toInt()
//        val addedProductQuantity = intent.getStringExtra("productQuantity")?.toInt()
//        Log.d("cartActivity", "$addedProductName")
//        binding.cartProductName.text = "$addedProductName"
//        binding.cartProductCost.text = "$addedProductCost x $addedProductQuantity = " +
//                (addedProductCost!! * addedProductQuantity!!).toString() + " р"
//
//        binding.backButton.setOnClickListener{
//            val intent = Intent(this@CartActivity, MainActivityBinding::class.java)
//            startActivity(intent)
//
//
//        }
//    }
}