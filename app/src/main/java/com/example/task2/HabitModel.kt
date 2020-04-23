package com.example.task2

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HabitModel() {
    private var db = AppDatabase.getInstance()

    fun createHabit(habit: Habit) {
        db.habitDao().insert(habit)
    }

    fun editHabit(habit: Habit) {
        db.habitDao().update(habit)
    }

    fun getHabitById(id: String): LiveData<Habit> {
        return db.habitDao().getHabitById(id)
    }

    fun getFilteredSortedHabits(filterString: String, isAscending: Boolean,
                                type: Constants.HabitType, sortType: Constants.SortType) : LiveData<List<Habit>> {
        if (isAscending) {
            if (sortType == Constants.SortType.TITLE)
                return db.habitDao().getFilteredTitleAscByField(filterString, if (type == Constants.HabitType.GOOD) 1 else 0)
            return db.habitDao().getFilteredDescrAscByField(filterString, if (type == Constants.HabitType.GOOD) 1 else 0)
        }
        if (sortType == Constants.SortType.TITLE)
            return db.habitDao().getFilteredTitleDescByField(filterString, if (type == Constants.HabitType.GOOD) 1 else 0)
        return db.habitDao().getFilteredDescrDescByField(filterString, if (type == Constants.HabitType.GOOD) 1 else 0)
    }

    private fun getFilteredHabits(filterString: String, type: Constants.HabitType,
                                  sortType: Constants.SortType) : LiveData<List<Habit>> {
        val filterField = if (sortType == Constants.SortType.TITLE) "title" else "description"
        return db.habitDao().getFilteredByField(filterField, filterString, if (type == Constants.HabitType.GOOD) 1 else 0)
    }

    fun getAll() : LiveData<List<Habit>> {
        return db.habitDao().getAll()
    }

    fun getHabitsByType(isGoodHabit: Boolean): LiveData<List<Habit>> {
        return db.habitDao().getAllByType(if (isGoodHabit) 1 else 0)
    }
}