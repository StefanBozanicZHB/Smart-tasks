package com.zhbcompany.smarttasks.domain.model

enum class TaskStatus(val value: String) {
    UNRESOLVED("Unresolved"),
    RESOLVED("Resolved"),
    CANT_RESOLVE("Can't resolve")
}
