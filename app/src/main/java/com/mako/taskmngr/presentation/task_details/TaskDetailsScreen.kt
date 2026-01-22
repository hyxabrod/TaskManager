package com.mako.taskmngr.presentation.task_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mako.taskmngr.core.theme.Dimens
import com.mako.taskmngr.core.theme.TaskManagerTheme
import com.mako.taskmngr.core.theme.TmTypography
import com.mako.taskmngr.core.theme.brand
import com.mako.taskmngr.core.theme.screenTitle
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import com.mako.taskmngr.presentation.task_details.entities.TaskDetailsEffect
import com.mako.taskmngr.presentation.task_details.entities.TaskDetailsIntent
import kotlinx.coroutines.launch

private object DescriptionFieldConfig {
    const val MIN_LINES_PORTRAIT = 4
    const val MAX_LINES_PORTRAIT = 8
    const val MIN_LINES_LANDSCAPE = 2
    const val MAX_LINES_LANDSCAPE = 4
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    onNavigateBack: () -> Unit,
    viewModel: TaskDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TaskDetailsEffect.ShowSnackbar -> {
                    keyboardController?.hide()
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message)
                    }
                }

                TaskDetailsEffect.BackNavigation -> onNavigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TaskDetailsTopBar(
                onNavigateBack = {
                    viewModel.handleIntent(TaskDetailsIntent.BackNavigation)
                },
                onDeleteClick = {
                    viewModel.handleIntent(TaskDetailsIntent.DeleteTask)
                },
                onSaveClick = { viewModel.handleIntent(TaskDetailsIntent.SaveTask) },
                isSaveEnabled = state.title.isNotEmpty(),
                canDelete = state.canDelete,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        TaskDetailsContent(
            title = state.title,
            description = state.description,
            status = state.status,
            onTitleChange = { viewModel.handleIntent(TaskDetailsIntent.Title(it)) },
            onDescriptionChange = { viewModel.handleIntent(TaskDetailsIntent.Description(it)) },
            onStatusChange = { viewModel.handleIntent(TaskDetailsIntent.Status(it)) },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Dimens.Padding16),
            focusRequester
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskDetailsTopBar(
    onNavigateBack: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isSaveEnabled: Boolean,
    canDelete: Boolean,
) {
    TaskManagerTheme(darkTheme = true) {
        TopAppBar(
            title = {
                Text(
                    "Task Details",
                    color = MaterialTheme.colorScheme.brand,
                    style = TmTypography.screenTitle
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.brand
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = onDeleteClick,
                    enabled = canDelete,
                    modifier = Modifier.scale(1.5f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Save",
                        tint = if (canDelete) {
                            MaterialTheme.colorScheme.brand
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        }
                    )
                }
                IconButton(
                    onClick = onSaveClick,
                    enabled = isSaveEnabled,
                    modifier = Modifier.scale(1.5f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = if (isSaveEnabled) {
                            MaterialTheme.colorScheme.brand
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        }
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskDetailsContent(
    title: String,
    description: String,
    status: TaskPresentationStatus,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onStatusChange: (TaskPresentationStatus) -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester
) {
    val configuration = LocalConfiguration.current
    val isLandscape =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    val descriptionMinLines = if (isLandscape) {
        DescriptionFieldConfig.MIN_LINES_LANDSCAPE
    } else {
        DescriptionFieldConfig.MIN_LINES_PORTRAIT
    }
    val descriptionMaxLines = if (isLandscape) {
        DescriptionFieldConfig.MAX_LINES_LANDSCAPE
    } else {
        DescriptionFieldConfig.MAX_LINES_PORTRAIT
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding16)
    ) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            singleLine = true,
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = descriptionMinLines,
            maxLines = descriptionMaxLines
        )

        TaskStatusDropdown(
            status = status,
            onStatusChange = onStatusChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskStatusDropdown(
    status: TaskPresentationStatus,
    onStatusChange: (TaskPresentationStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = status.value,
            onValueChange = {},
            readOnly = true,
            label = { Text("Status") },
            leadingIcon = { StatusIcon(status) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TaskPresentationStatus.entries.forEach { statusOption ->
                DropdownMenuItem(
                    text = { Text(statusOption.value) },
                    leadingIcon = { StatusIcon(statusOption) },
                    onClick = {
                        onStatusChange(statusOption)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun StatusIcon(status: TaskPresentationStatus) {
    Icon(
        imageVector = status.icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary
    )
}
