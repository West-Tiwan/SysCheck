package com.example.syscheck.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.syscheck.SysCheckApplication
import com.example.syscheck.data.local.dao.TaskDAO
import com.example.syscheck.data.local.entity.TaskEntity
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDAO): ViewModel() {
    val tasks = dao.getTaskAll().asLiveData()

    fun getTask(id: Int) = viewModelScope.launch {
        dao.getTaskId(id)
    }

    fun insertTask(task: TaskEntity) = viewModelScope.launch {
        dao.insertTask(task)
    }

    fun deleteTask(id: Int) = viewModelScope.launch {
        val task = dao.getTaskId(id)
        if (task != null) {
            dao.deleteTask(task)
        }
    }
    
    fun deleteTaskById(id: Int) = viewModelScope.launch {
        dao.deleteTaskById(id)
    }
}

// Alternative AndroidViewModel version that can be instantiated by default
class AndroidTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: TaskDAO by lazy {
        (application as SysCheckApplication).database.taskDao()
    }
    
    val tasks = dao.getTaskAll().asLiveData()

    fun getTask(id: Int) = viewModelScope.launch {
        dao.getTaskId(id)
    }

    fun insertTask(task: TaskEntity) = viewModelScope.launch {
        dao.insertTask(task)
    }

    fun deleteTask(id: Int) = viewModelScope.launch {
        val task = dao.getTaskId(id)
        if (task != null) {
            dao.deleteTask(task)
        }
    }
    
    fun deleteTaskById(id: Int) = viewModelScope.launch {
        dao.deleteTaskById(id)
    }
}

class TaskViewModelFactory(private val dao: TaskDAO) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}