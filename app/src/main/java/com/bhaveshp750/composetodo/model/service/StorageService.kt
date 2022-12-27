package com.bhaveshp750.composetodo.model.service

import com.bhaveshp750.composetodo.model.Task
import kotlinx.coroutines.flow.Flow

interface StorageService {
  val tasks: Flow<List<Task>>

  suspend fun getTask(taskId: String): Task?

  suspend fun save(task: Task): String
  suspend fun update(task: Task)
  suspend fun delete(taskId: String)
  suspend fun deleteAllForUser(userId: String)
}