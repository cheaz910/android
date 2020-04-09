package com.example.task2

import android.util.Log
import androidx.lifecycle.*

class HabitsViewModel(private var habitType: Constants.HabitType) : ViewModel() {
    private var mediatorHabits: MediatorLiveData<List<Habit>> = MediatorLiveData()
    private var lastHabits: LiveData<List<Habit>> = MutableLiveData()

    // val habits: LiveData<List<Habit>> = mutableHabits
    private var filterString = ""
    private var isAscending = true
    private var sortType = Constants.SortType.TITLE
    private val habitModel = HabitModel()

    init {
        load()
    }

    private fun load() {
        changeHabits()
    }

    fun getHabits() : LiveData<List<Habit>> {
        return mediatorHabits
    }

    fun filterByString(filterString: String, sortType: Constants.SortType) {
        this.filterString = filterString
        this.sortType = sortType
        changeHabits()
    }

    fun sortBy(isAscending: Boolean, sortType: Constants.SortType) {
        this.isAscending = isAscending
        this.sortType = sortType
        changeHabits()
    }

    fun selectItemSelected(sortType: Constants.SortType) {
        this.sortType = sortType
        changeHabits()
    }

    private fun changeHabits() {
        mediatorHabits.removeSource(lastHabits)
        lastHabits = habitModel.getFilteredSortedHabits(filterString, isAscending, habitType, sortType)
        mediatorHabits.addSource(lastHabits, Observer {
            mediatorHabits.setValue(it)
        })
    }
}