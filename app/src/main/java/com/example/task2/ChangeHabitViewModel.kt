package com.example.task2

import android.content.Context
import android.graphics.ColorSpace
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_add_habit.*

class ChangeHabitViewModel(private val habitId: Int?) : ViewModel() {
    private var mutableChangedHabit: LiveData<Habit> = MutableLiveData()

    private val habitModel = HabitModel()

    init {
        load()
    }

    fun getChangedHabit() : LiveData<Habit> {
        return mutableChangedHabit
    }

    private fun load() {
        if (habitId != null)
            mutableChangedHabit = habitModel.getHabitById(habitId)
    }

    fun addHabit(title: String?, description: String?, priority: String?,
                 type: Constants.HabitType, count: String?, frequency: String?) {
        habitModel.createHabit(title, description, priority, type, count, frequency)
    }

    fun changeHabit(id: Int, title: String?, description: String?, priority: String?,
                    type: Constants.HabitType, count: String?, frequency: String?) {
        habitModel.editHabit(id, title, description, priority, type, count, frequency)
    }
}