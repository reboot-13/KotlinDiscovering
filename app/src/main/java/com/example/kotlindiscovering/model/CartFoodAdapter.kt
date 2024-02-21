package com.example.kotlindiscovering.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindiscovering.databinding.ItemFoodCartBinding

class CartFoodAdapter (val cartFoodList: List<Food>) : RecyclerView.Adapter<CartFoodAdapter.CartFoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodCartBinding.inflate(inflater, parent, false)
        return CartFoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartFoodList.size
    }

    override fun onBindViewHolder(holder: CartFoodViewHolder, position: Int) {
        val cartFood = cartFoodList[position]
        with(holder.binding){
            holder.itemView.tag = cartFood
            foodLabelTextView.text = cartFood.name
            foodCostTextView.text = cartFood.cost
            imgView.setImageResource(cartFood.image)

        }
    }

    class CartFoodViewHolder (val binding: ItemFoodCartBinding) :RecyclerView.ViewHolder(binding.root)
}