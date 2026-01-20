package com.mako.taskmngr.presentation.task_list.entities

sealed interface TaskManagerListEffect {
    data class ShowToast(val message: String) : TaskManagerListEffect
    object ScrollToTop : TaskManagerListEffect
    data class Navigation(val id: Long) : TaskManagerListEffect
}