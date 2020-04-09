package com.example.task2

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.android.parcel.Parcelize

class HabitModel() {
    private var db = AppDatabase.getInstance()

    fun createHabit(title: String?, description: String?, priority: String?, type: Constants.HabitType,
                    count: String?, frequency: String?) {
        val habit = Habit(title, description, priority, type == Constants.HabitType.GOOD, count, frequency)
        db.habitDao().insert(habit)
    }

    fun getHabitById(id: Int): LiveData<Habit> {
        return db.habitDao().getHabitById(id)
    }

    fun editHabit(id: Int, title: String?, description: String?, priority: String?, type: Constants.HabitType,
                  count: String?, frequency: String?) {
        val habit = Habit(title, description, priority, type == Constants.HabitType.GOOD, count, frequency)
        habit.id = id
        db.habitDao().update(habit)
    }

    fun getFilteredSortedHabits(filterString: String, isAscending: Boolean,
                                type: Constants.HabitType, sortType: Constants.SortType) : LiveData<List<Habit>> {
        if (isAscending) {
            if (sortType == Constants.SortType.TITLE)
                return db.habitDao().getFilteredTitleAscByField(filterString, type == Constants.HabitType.GOOD)
            return db.habitDao().getFilteredDescrAscByField(filterString, type == Constants.HabitType.GOOD)
        }
        if (sortType == Constants.SortType.TITLE)
            return db.habitDao().getFilteredTitleDescByField(filterString, type == Constants.HabitType.GOOD)
        return db.habitDao().getFilteredDescrDescByField(filterString, type == Constants.HabitType.GOOD)
    }

    private fun getFilteredHabits(filterString: String, type: Constants.HabitType,
                                  sortType: Constants.SortType) : LiveData<List<Habit>> {
        val filterField = if (sortType == Constants.SortType.TITLE) "title" else "description"
        return db.habitDao().getFilteredByField(filterField, filterString, type == Constants.HabitType.GOOD)
    }

    fun getAll() : LiveData<List<Habit>> {
        return db.habitDao().getAll()
    }

    fun getHabits(isGoodHabit: Boolean): LiveData<List<Habit>> {
        return db.habitDao().getAllByType(isGoodHabit)
    }
}