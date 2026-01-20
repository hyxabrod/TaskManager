package com.mako.taskmngr.presentation.entities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlagCircle
import androidx.compose.material.icons.filled.IncompleteCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.ui.graphics.vector.ImageVector
import com.mako.taskmngr.core.PresentationEntity

data class TaskPresentationEntity(
    val id: Long,
    val title: String,
    val description: String,
    val status: TaskPresentationStatus,
) : PresentationEntity {
    companion object {
        const val NO_ID = -1L
    }
}

enum class TaskPresentationStatus(val value: String, val icon: ImageVector) : PresentationEntity {
    TODO("TODO", Icons.Outlined.Circle),
    IN_PROGRESS("IN PROGRESS", Icons.Default.IncompleteCircle),
    DONE("DONE", Icons.Default.FlagCircle);
}
