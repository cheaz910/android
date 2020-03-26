package com.example.task2

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Habit(
    val title: String?,
    val description: String?,
    val priority: String?,
    val type: String?,
    val count: String?,
    val frequency: String?
) : Parcelable {}