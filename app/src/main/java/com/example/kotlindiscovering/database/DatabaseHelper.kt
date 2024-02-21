package com.example.kotlindiscovering.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "food.db"
        private const val  DATABASE_VERSION = 1

        private const val CREATE_TABLE_FOOD = """       
            CREATE TABLE food (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            food_name TEXT,
            food_cost TEXT
            )
        """
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // create the food table
        db!!.execSQL(CREATE_TABLE_FOOD)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // drop the food table if it exists
        db!!.execSQL("DROP TABLE IF EXISTS food")

        // create the new food table
        onCreate(db)
    }
}