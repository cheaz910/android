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

    @Query("SELECT * FROM habit WHERE description LIKE '%' || :filterString || '%' AND type = :type ORDER BY description DESC")
    fun getFilteredDescrDescByField(filterString: String,
                                    type: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE description LIKE '%' || :filterString || '%' AND type = :type ORDER BY description ASC")
    fun getFilteredDescrAscByField(filterString: String,
                                   type: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE title LIKE '%' || :filterString || '%' AND type = :type ORDER BY title DESC")
    fun getFilteredTitleDescByField(filterString: String,
                                    type: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE title LIKE '%' || :filterString || '%' AND type = :type ORDER BY title ASC")
    fun getFilteredTitleAscByField(filterString: String,
                                   type: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE :filterField LIKE :description AND type = :type")
    fun getFilteredByField(filterField: String, description: String, type: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE title LIKE :title AND type = :type")
    fun getFilteredByTitle(title: String, type: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE type = :type")
    fun getAllByType(type: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE id = :id")
    fun getHabitById(id: String) : LiveData<Habit>

    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Insert
    fun insertAll(habits: List<Habit>)

    @Query("DELETE FROM habit")
    fun deleteAll()
}