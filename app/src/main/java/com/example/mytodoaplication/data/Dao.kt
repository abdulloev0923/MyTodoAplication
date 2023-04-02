package com.example.mytodoaplication.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Dao

@Dao
interface Dao {

    @Insert
    suspend fun addTodo(todo: Todo)
    @Query("SELECT * FROM todo_table")
    suspend fun getAllTodo():List<Todo>
    @Update
    suspend fun updateTodo(todo: Todo)
    @Delete
    suspend fun deleteTodo(todo: Todo)
    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodo()

}