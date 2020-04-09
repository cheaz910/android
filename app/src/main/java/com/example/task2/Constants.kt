package com.example.task2

class Constants {
    enum class HabitType {
        BAD, GOOD
    }

    enum class SortType(val value: Int) {
        TITLE(0), DESCRIPTION(1)
    }

    companion object {
        val priorities = mutableMapOf("Высокий" to 1, "Низкий" to 0)
    }
}