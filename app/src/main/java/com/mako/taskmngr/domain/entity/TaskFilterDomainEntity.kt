package com.mako.taskmngr.domain.entity

data class TaskFilterDomainEntity(
    val searchQuery: String = "",
    val selectedStatuses: Set<TaskDomainStatus> = emptySet()
)
