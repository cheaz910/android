package com.example.task2

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
data class Habit(
    var title: String?,
    var description: String?,
    var priority: String?,
    var isGoodHabit: Boolean,
    var count: String?,
    var frequency: String?
) {
    @PrimaryKey(autoGenerate = true) var id = 0
}