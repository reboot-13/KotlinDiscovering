package com.example.kotlindiscovering.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.kotlindiscovering.model.Food

class FoodDatabase(context: Context) {
    private val databaseHelper = DatabaseHelper(context)

    fun insert (name: String, cost: String) {  //add image
        // get the writable database
        val db = databaseHelper.writableDatabase

        // create the ContentValues object
        val values = ContentValues().apply {
            put("food_name", name)
            put("food_cost", cost)
//            put("food_image", image)

        }
        // insert the data into the table
        db.insert("food", null, values)

        // close the database connection
        db.close()
    }

    @SuppressLint("Range")
    fun getAll(): List<Food> {
        val list = mutableListOf<Food>()

        // get the readable database
        val db = databaseHelper.writableDatabase

        // select all data from the table
        val cursor = db.rawQuery("SELECT * FROM food", null)

        // iterate through the cursor and add the data to the list
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("food_id"))
            val name = cursor.getString(cursor.getColumnIndex("food_name"))
            val cost = cursor.getString(cursor.getColumnIndex("food_cost"))
            val image = cursor.getString(cursor.getColumnIndex("food_cost"))
//            val image = cursor.getBlob(cursor.getColumnIndex("food_image"))
//            list.add(Food(id, name, cost, image)) //add image
        }

        // close the cursor and database connection
        cursor.close()
        db.close()
        return list
    }
    fun update (id: Int, name: String, cost: String) {  //add image
        // get the writable database
        val db = databaseHelper.writableDatabase

        // create the ContentValues object
        val values = ContentValues().apply {
            put("id", id)
            put("food_name", name)
            put("food_cost", cost)
//            put("food_image", image)
        }
        // update the data in the table
        db.update("food", values, "id = ?", arrayOf(id.toString()))

        // close the database connection
        db.close()
    }

    fun delete (id: Int) {
        // get the writable database
        val db = databaseHelper.writableDatabase

        // delete the data from the table
        db.delete("food", "id = ?", arrayOf(id.toString()))

        // close the database connection
        db.close()
    }

}