package com.example.kotlindiscovering.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindiscovering.MainActivity
import com.example.kotlindiscovering.R
import com.example.kotlindiscovering.databinding.ActivityMainBinding
import com.example.kotlindiscovering.databinding.ItemFoodBinding
import com.google.android.material.button.MaterialButton

//
//sealed class FoodAdapterViewHolder {
//
//    data class Text(val name: String)
//
//    data class Item(val food: Food)
//}

interface FoodActionListener {
    fun onFoodDetails(food: Food)

    fun onFoodAddToCart(food: Food)
}

class FoodAdapter (private val actionListener: FoodActionListener
): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(), View.OnClickListener{
    var foodList: List<Food> = emptyList()

    override fun getItemCount(): Int = foodList.size    //так можно реализовать метод. Этот - возвращает количество еды в списке

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {   //используется когда recyclerView хочет создать новый элемент списка
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.foodToCart.setOnClickListener(this)

            return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {    //используется чтобы обновить элемент списка
        val food = foodList[position]
        with(holder.binding) {
            holder.itemView.tag = food
            foodToCart.tag = food
            foodLabelTextView.text = food.name
            foodCostTextView.text = food.cost
            imgView.setImageResource(food.image)

            foodToCart.setOnClickListener(){
               holder.showChangeQuantityMenu()

            }

            foodQuantityUp.setOnClickListener(){
                holder.changeQuantity(true)
            }

            foodQuantityDown.setOnClickListener(){
                holder.changeQuantity(false)
            }

        }
    }

    override fun onClick(v: View) {
        val food = v.tag as Food
        when (v.id){
            R.id.foodToCart -> {
                actionListener.onFoodAddToCart(food)

            }

            else -> {
                actionListener.onFoodDetails(food)
            }
        }
    }


    class FoodViewHolder (val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showChangeQuantityMenu(){
            with(binding){
                foodToCart.isVisible = false
                foodQuantityDown.isVisible = true
                foodQuantityUp.isVisible = true
                editFoodQuantity.isVisible = true
                editFoodQuantity.setText("1")
            }

        }

        fun changeQuantity(isUp: Boolean) {
            val currentQuantityValue = binding.editFoodQuantity.text.toString().toInt()
            if (isUp) {
                val newQuantityValue = currentQuantityValue + 1
                binding.editFoodQuantity.setText(newQuantityValue.toString())
            } else {
                if (currentQuantityValue == 1) {
                    with(binding){
                        editFoodQuantity.isVisible = false
                        foodQuantityUp.isVisible = false
                        foodQuantityDown.isVisible= false
                        foodToCart.isVisible = true
                    }

                } else {
                    val newQuantityValue = currentQuantityValue - 1
                    binding.editFoodQuantity.setText(newQuantityValue.toString())
                }
            }
        }
    }
}





