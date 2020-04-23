package com.example.task2

class Constants {
    enum class HabitType {
        BAD, GOOD
    }

    enum class SortType(val value: Int) {
        TITLE(0), DESCRIPTION(1)
    }

    companion object {
        val prioritiesToInt = mutableMapOf("Высокий" to 2, "Низкий" to 1, "Без приоритета" to 0)
        val intToPriorities = mutableMapOf(2 to "Высокий", 1 to "Низкий", 0 to "Без приоритета")
    }
}