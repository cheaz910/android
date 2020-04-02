package com.example.task2

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize

@Parcelize
class HabitModel : Parcelable {
    companion object {
        private var habits = mutableListOf<Habit>(
            Habit(
                0, "Привычка1", "Описание привычки1", "Высокий",
                Constants.HabitType.BAD,
                "", "1"
            ),
            Habit(
                1, "Привычка1", "АОписание привычки1", "Высокий",
                Constants.HabitType.GOOD,
                "", "1"
            ),
            Habit(
                2, "АбвПривычка1", "БОписание привычки1", "Высокий",
                Constants.HabitType.GOOD,
                "", "1"
            ),
            Habit(
                3, "БгвдПривычка1", "ВОписание привычки1", "Высокий",
                Constants.HabitType.GOOD,
                "", "1"
            ),
            Habit(
                4, "Привasdfычка1", "ГОписание привычки1", "Высокий",
                Constants.HabitType.GOOD,
                "", "1"
            ),
            Habit(
                5, "Привычкаfdsafd1", "Описание привычки1", "Высокий",
                Constants.HabitType.GOOD,
                "", "1"
            )
        )
        private var idCounter = 6;

        fun createHabit(title: String?, description: String?, priority: String?, type: Constants.HabitType,
                        count: String?, frequency: String?) {
            val habit = Habit(idCounter, title, description, priority, type, count, frequency)
            idCounter++
            habits.add(habit)
        }

        fun getHabitById(id: Int): Habit? {
            var result = getHabits(Constants.HabitType.GOOD).singleOrNull { s -> s.id == id }
            if (result == null) {
                result = getHabits(Constants.HabitType.BAD).singleOrNull { s -> s.id == id}
            }
            return result
        }

        fun editHabit(id: Int, title: String?, description: String?, priority: String?, type: Constants.HabitType,
                      count: String?, frequency: String?) {
            val habit = getHabitById(id)!!
            habit.title = title
            habit.description = description
            habit.priority = priority
            habit.type = type
            habit.count = count
            habit.frequency = frequency
        }

        fun getFilteredSortedHabits(filterString: String, isAscending: Boolean,
                                    type: Constants.HabitType, sortType: Constants.SortType) : List<Habit> {
            val comparator: Comparator<Habit> = when (sortType) {
                Constants.SortType.TITLE -> if (isAscending)
                    compareBy { it.title }
                else
                    compareByDescending { it.title }
                Constants.SortType.DESCRIPTION -> if (isAscending)
                    compareBy { it.description }
                else
                    compareByDescending { it.description }
            }
            return getFilteredHabits(filterString, type, sortType).sortedWith(comparator)
        }

        private fun getFilteredHabits(filterString: String, type: Constants.HabitType,
                                      sortType: Constants.SortType) : List<Habit> {
            return if (sortType == Constants.SortType.TITLE)
                getHabits(type).filter { s -> s.title!!.contains(filterString)}
            else
                getHabits(type).filter { s -> s.description!!.contains(filterString)}

        }

        private fun getHabits(type: Constants.HabitType): List<Habit> {
            return if (type == Constants.HabitType.GOOD)
                habits.filter { it.type == Constants.HabitType.GOOD }
            else
                habits.filter { it.type == Constants.HabitType.BAD }
        }
    }
}