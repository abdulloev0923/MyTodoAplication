package com.example.mytodoaplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "todo_table")
data class Todo(
    @ColumnInfo(name = "title") var title:String = "",
    @ColumnInfo(name = "description") var description:String = ""
):
    Serializable {
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}
