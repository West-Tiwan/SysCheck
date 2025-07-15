package com.example.syscheck.data.repository

import com.example.syscheck.data.local.dao.TaskDAO
import com.example.syscheck.data.local.entity.TaskEntity

class TaskRepository(private val dao: TaskDAO) {
    val tasks = dao.getTaskAll()

    suspend fun getTask(id: Int) = dao.getTaskId(id)

    suspend fun insertTask(task: TaskEntity) = dao.insertTask(task)

    suspend fun deleteTask(id: Int) {
        val task = dao.getTaskId(id)
        dao.deleteTask(task)
    }
}