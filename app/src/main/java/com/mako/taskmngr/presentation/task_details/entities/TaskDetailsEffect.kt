package com.mako.taskmngr.presentation.task_details.entities

sealed interface TaskDetailsEffect {
    data class ShowSnackbar(val message: String) : TaskDetailsEffect
    object BackNavigation : TaskDetailsEffect
}