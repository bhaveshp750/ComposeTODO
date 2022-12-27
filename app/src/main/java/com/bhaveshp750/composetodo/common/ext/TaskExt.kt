package com.bhaveshp750.composetodo.common.ext

import com.bhaveshp750.composetodo.model.Task

fun Task?.hasDueDate(): Boolean {
  return this?.dueDate.orEmpty().isNotBlank()
}

fun Task?.hasDueTime(): Boolean {
  return this?.dueTime.orEmpty().isNotBlank()
}

