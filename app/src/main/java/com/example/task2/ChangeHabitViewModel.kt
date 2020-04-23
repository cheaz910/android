package com.example.task2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class ChangeHabitViewModel(private val habitId: String?) : ViewModel(), CoroutineScope {
    private var mutableChangedHabit: LiveData<Habit> = MutableLiveData()

    private val habitModel = HabitModel()
    private val api: HabitApi = NetworkService.instance.jSONApi

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + CoroutineExceptionHandler { _, e -> throw e }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

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

    fun handleHabitForm(title: String?, description: String?, priority: Int?,
                        type: Constants.HabitType, count: Int?, frequency: Int?,
                        id: String?) {
        if (id == null) {
            addHabit(title, description, priority, type, count, frequency)
        } else {
            changeHabit(id, title, description, priority, type, count, frequency)
        }
    }

    private fun addHabit(title: String?, description: String?, priority: Int?,
                         type: Constants.HabitType, count: Int?, frequency: Int?) = launch {
        val intType = if (type == Constants.HabitType.GOOD) 1 else 0
        val habit = SimpleHabit(title, description, priority, intType, count, frequency)
        withContext(Dispatchers.IO) {
            val uid = JSONObject(api.add(habit).string()).get("uid").toString()
            val habitWithUID = habit.getHabitWithUID(uid)
            habitModel.createHabit(habitWithUID)
        }
    }

    private fun changeHabit(uid: String, title: String?, description: String?, priority: Int?,
                            type: Constants.HabitType, count: Int?, frequency: Int?) = launch {
        val intType = if (type == Constants.HabitType.GOOD) 1 else 0
        val habit = Habit(title, description, priority, intType, count, frequency, uid)
        withContext(Dispatchers.IO) {
            val uidResponse = JSONObject(api.update(habit).string()).get("uid").toString()
            if (uidResponse == uid) {
                habitModel.editHabit(habit)
            }
        }
    }
}