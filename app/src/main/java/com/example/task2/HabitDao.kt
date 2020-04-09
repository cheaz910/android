package com.example.task2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll() : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE description LIKE '%' || :filterString || '%' AND isGoodHabit = :type ORDER BY description DESC")
    fun getFilteredDescrDescByField(filterString: String,
                                    type: Boolean) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE description LIKE '%' || :filterString || '%' AND isGoodHabit = :type ORDER BY description ASC")
    fun getFilteredDescrAscByField(filterString: String,
                                   type: Boolean) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE title LIKE '%' || :filterString || '%' AND isGoodHabit = :type ORDER BY title DESC")
    fun getFilteredTitleDescByField(filterString: String,
                                    type: Boolean) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE title LIKE '%' || :filterString || '%' AND isGoodHabit = :type ORDER BY title ASC")
    fun getFilteredTitleAscByField(filterString: String,
                                   type: Boolean) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE :filterField LIKE :description AND isGoodHabit = :type")
    fun getFilteredByField(filterField: String, description: String, type: Boolean) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE title LIKE :title AND isGoodHabit = :type")
    fun getFilteredByTitle(title: String, type: Boolean) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE isGoodHabit = :isGoodHabit")
    fun getAllByType(isGoodHabit: Boolean) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE id = :id")
    fun getHabitById(id: Int) : LiveData<Habit>

    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)
}