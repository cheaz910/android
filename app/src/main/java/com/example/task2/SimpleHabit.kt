package com.example.task2

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
data class SimpleHabit(
    var title: String?,
    var description: String?,
    var priority: Int?,
    var type: Int,
    var count: Int?,
    var frequency: Int?
) {
    var date = Date().time.toInt()
    fun getHabitWithUID(uid: String) : Habit {
        return Habit(title, description, priority, type, count, frequency, uid)
    }
}