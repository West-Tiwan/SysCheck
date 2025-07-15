package com.example.syscheck.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title: String,
    var desc: String
)