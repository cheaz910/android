package com.example.task2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Habit::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        private var instance: AppDatabase? = null
        fun init(context: Context) {
            instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "Habit"
            ).fallbackToDestructiveMigration().build()
        }

        fun getInstance(): AppDatabase {
            return instance as AppDatabase
        }
    }
}