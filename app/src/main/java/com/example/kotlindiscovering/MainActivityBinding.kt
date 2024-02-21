package com.example.kotlindiscovering

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.example.kotlindiscovering.databinding.ActivityMainBinding
import java.util.Random

class MainActivityBinding : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//
//        binding.filaQuantityUp.setOnClickListener {
//            changeQuantity(true)
//        }
//
//        binding.filaQuantityDown.setOnClickListener {
//            changeQuantity(false)
//        }
//
//        binding.filaToCart.setOnClickListener {
//            showCart()
//        }
//
//        binding.cartButton.setOnClickListener{
//            val cartDataIntent = Intent(this@MainActivityBinding, cartActivity::class.java)
//            val bundle = Bundle()
//            bundle.putString("productCost", binding.filaCost.text.toString())
//            bundle.putString("productLabel", binding.filalabel.text.toString())
//            bundle.putString("productQuantity", binding.editFilaQuantity.text.toString())
//            cartDataIntent.putExtras(bundle)
//            startActivity(cartDataIntent)
//        }
//    }
//
//
//    private fun changeQuantity(isUp: Boolean) {
//        val currentQuantityValue = binding.editFilaQuantity.text.toString().toInt()
//        if (isUp) {
//            val newQuantityValue = currentQuantityValue + 1
//            binding.editFilaQuantity.setText(newQuantityValue.toString())
//        } else {
//            if (currentQuantityValue > 1) {
//                val newQuantityValue = currentQuantityValue - 1
//                binding.editFilaQuantity.setText(newQuantityValue.toString())
//            } else Toast.makeText(this, "Нельзя сделать меньше", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    private fun showCart() {
//        binding.cartButton.isInvisible = false
//        with(binding.cartValue) {
//            isInvisible = false
//            text = binding.editFilaQuantity.text.toString()
//        }
    }

}