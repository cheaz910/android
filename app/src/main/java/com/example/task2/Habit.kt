package com.example.task2

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Habit(
    val id: Int,
    var title: String?,
    var description: String?,
    var priority: String?,
    var type: Constants.HabitType,
    var count: String?,
    var frequency: String?
) : Parcelable {}