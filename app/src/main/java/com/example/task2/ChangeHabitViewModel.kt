package com.example.task2

import android.graphics.ColorSpace
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_add_habit.*

class ChangeHabitViewModel(private val habitId: Int?) : ViewModel() {
    private val mutableNewHabit: MutableLiveData<Habit?> = MutableLiveData()
    private val mutableChangedHabit: MutableLiveData<Habit?> = MutableLiveData()

    val newHabit: LiveData<Habit?> = mutableNewHabit
    val changedHabit: LiveData<Habit?> = mutableChangedHabit

    init {
        load()
    }

    private fun load() {
        if (habitId != null)
            mutableChangedHabit.postValue(HabitModel.getHabitById(habitId))
    }

    fun addHabit(title: String?, description: String?, priority: String?,
                 type: Constants.HabitType, count: String?, frequency: String?) {
        HabitModel.createHabit(title, description, priority, type, count, frequency)
    }

    fun changeHabit(id: Int, title: String?, description: String?, priority: String?,
                    type: Constants.HabitType, count: String?, frequency: String?) {
        HabitModel.editHabit(id, title, description, priority, type, count, frequency)
    }
}