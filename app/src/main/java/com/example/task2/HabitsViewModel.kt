package com.example.task2

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class HabitsViewModel(private val habitType: Constants.HabitType) : ViewModel(), Parcelable {
    private val mutableHabits: MutableLiveData<List<Habit>?> = MutableLiveData()

    val habits: LiveData<List<Habit>?> = mutableHabits

    private var filterString = ""
    private var isAscending = true
    private var sortType = Constants.SortType.TITLE

    init {
        load()
    }

    private fun load() {
        changeHabits()
    }

    fun filterByString(filterString: String) {
        this.filterString = filterString
        changeHabits()
    }

    fun sortBy(isAscending: Boolean, sortType: Constants.SortType) {
        this.isAscending = isAscending
        this.sortType = sortType
        changeHabits()
    }

    private fun changeHabits() {
        mutableHabits.postValue(ArrayList<Habit>(
            HabitModel.getFilteredSortedHabits(filterString, isAscending, habitType, sortType)))
    }
}