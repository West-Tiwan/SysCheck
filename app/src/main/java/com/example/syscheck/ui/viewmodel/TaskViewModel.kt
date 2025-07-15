package com.example.syscheck.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.syscheck.data.local.entity.TaskEntity
import com.example.syscheck.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository): ViewModel() {
    val tasks = repository.tasks.asLiveData()

    fun getTask(id: Int) = viewModelScope.launch {
        repository.getTask(id)
    }

    fun insertTask(task: TaskEntity) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun deleteTask(id: Int) = viewModelScope.launch {
        repository.deleteTask(id)
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}