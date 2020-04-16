package com.example.task2

import android.util.Log
import androidx.lifecycle.*

class HabitsViewModel(private var habitType: Constants.HabitType) : ViewModel() {
    private var mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()
    private var habitsList: List<Habit> = listOf()
    private var dbLiveData: LiveData<List<Habit>>? = null

    val habits: LiveData<List<Habit>> = mutableHabits
    private var filterString = ""
    private var isAscending = true
    private var sortType = Constants.SortType.TITLE
    private val habitModel = HabitModel()

    init {
        load()
    }

    private fun load() {
        dbLiveData = habitModel.getHabitsByType(habitType == Constants.HabitType.GOOD)
        dbLiveData!!.observeForever {
            habitsList = it
            changeHabits()
        }
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

    fun getFilteredSortedHabits() : List<Habit> {
        val comparator: Comparator<Habit> = when (this.sortType) {
            Constants.SortType.TITLE -> if (this.isAscending)
                compareBy { it.title }
            else
                compareByDescending { it.title }
            Constants.SortType.DESCRIPTION -> if (this.isAscending)
                compareBy { it.description }
            else
                compareByDescending { it.description }
        }
        return getFilteredHabits().sortedWith(comparator)
    }

    private fun getFilteredHabits() : List<Habit> {
        return if (this.sortType == Constants.SortType.TITLE)
            getHabits().filter { s -> s.title!!.contains(this.filterString)}
        else
            getHabits().filter { s -> s.description!!.contains(this.filterString)}

    }

    private fun getHabits(): List<Habit> {
        return if (this.habitType == Constants.HabitType.GOOD)
            habitsList.filter { it.isGoodHabit }
        else
            habitsList.filter { !it.isGoodHabit }
    }

    private fun changeHabits() {
        mutableHabits.postValue(ArrayList<Habit>(getFilteredSortedHabits()))
    }
}