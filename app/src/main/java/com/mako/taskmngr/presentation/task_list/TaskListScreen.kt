package com.mako.taskmngr.presentation.task_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mako.taskmngr.R
import com.mako.taskmngr.core.theme.Dimens
import com.mako.taskmngr.core.theme.TaskManagerTheme
import com.mako.taskmngr.core.theme.TmTypography
import com.mako.taskmngr.core.theme.brand
import com.mako.taskmngr.core.theme.screenTitle
import com.mako.taskmngr.presentation.entities.TaskItemPresentationEntity
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import com.mako.taskmngr.presentation.task_list.component.TaskItem
import com.mako.taskmngr.presentation.task_list.entities.TaskManagerListEffect
import com.mako.taskmngr.presentation.task_list.entities.TaskManagerListIntent
import com.mako.taskmngr.presentation.task_list.entities.TaskManagerListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksListScreen(onTaskClick: (Long) -> Unit, viewModel: TaskListViewModel = hiltViewModel()) {
    val tasks = viewModel.tasks.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is TaskManagerListEffect.Navigation -> onTaskClick(it.id)
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TaskManagerTheme(darkTheme = true) {
                TopAppBar(
                    title = {
                        Text(
                            "Task List",
                            color = MaterialTheme.colorScheme.brand,
                            style = TmTypography.screenTitle,
                            modifier = Modifier.padding(start = Dimens.Padding42)
                        )
                    },
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.handleIntent(TaskManagerListIntent.AddTask) }
            ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task") }
        }
    ) { paddingValues ->
        ScaffoldBody(tasks, paddingValues, state, viewModel)
    }
}

@Composable
private fun ScaffoldBody(
    tasks: LazyPagingItems<TaskItemPresentationEntity>,
    paddingValues: PaddingValues,
    state: TaskManagerListState,
    viewModel: TaskListViewModel
) {
    if (tasks.loadState.refresh is LoadState.Error) {
        Error(paddingValues)
    } else {
        val hasFilters = state.searchQuery.isNotEmpty() || state.selectedStatuses.isNotEmpty()

        if (tasks.loadState.refresh is LoadState.NotLoading && tasks.itemCount == 0 && !hasFilters) {
            NoData(paddingValues, viewModel)
        } else {
            Content(paddingValues, viewModel, tasks, state)
        }
    }
}

@Composable
private fun Error(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "We are very sorry!🥺\n\nAn error happened\nwhile fetching data\n",
            color = MaterialTheme.colorScheme.brand,
            style = TmTypography.bodyLarge,
        )
    }
}

@Composable
private fun NoData(paddingValues: PaddingValues, viewModel: TaskListViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "No tasks found",
            color = MaterialTheme.colorScheme.brand,
            style = TmTypography.bodyLarge,
        )
        Image(
            painter = painterResource(id = R.drawable.line_element_luxury),
            contentDescription = null,
            modifier = Modifier.padding(vertical = Dimens.Padding16)
        )
        Button(onClick = { viewModel.handleIntent(TaskManagerListIntent.GenerateTasks) }) {
            Text(
                "Generate",
                style = TmTypography.bodyMedium,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    paddingValues: PaddingValues,
    viewModel: TaskListViewModel,
    tasks: LazyPagingItems<TaskItemPresentationEntity>,
    state: TaskManagerListState,
) {
    val listState = rememberLazyListState()
    val hasFilters = state.searchQuery.isNotEmpty() || state.selectedStatuses.isNotEmpty()
    val isEmpty = tasks.itemCount == 0 && tasks.loadState.refresh is LoadState.NotLoading

    if (isEmpty && hasFilters) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FilterBar(
                searchQuery = state.searchQuery,
                selectedStatuses = state.selectedStatuses,
                onSearchQueryChange = {
                    viewModel.handleIntent(TaskManagerListIntent.UpdateSearchQuery(it))
                },
                onStatusToggle = {
                    viewModel.handleIntent(TaskManagerListIntent.ToggleStatus(it))
                }
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "No results found",
                        color = MaterialTheme.colorScheme.brand,
                        style = TmTypography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(Dimens.Padding8))
                    Text(
                        "Try adjusting your filters",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        style = TmTypography.bodyMedium,
                    )
                }
            }
        }
        return
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            start = Dimens.Padding16,
            end = Dimens.Padding16,
            bottom = Dimens.Padding16,
            top = 0.dp
        ),
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding8),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            FilterBar(
                searchQuery = state.searchQuery,
                selectedStatuses = state.selectedStatuses,
                onSearchQueryChange = {
                    viewModel.handleIntent(TaskManagerListIntent.UpdateSearchQuery(it))
                },
                onStatusToggle = {
                    viewModel.handleIntent(TaskManagerListIntent.ToggleStatus(it))
                }
            )
        }

        items(count = tasks.itemCount, key = { index -> tasks[index]?.id ?: index }) { index ->
            val task = tasks[index]
            if (task != null) {
                TaskItem(
                    title = task.title,
                    status = task.status,
                    onClick = {
                        viewModel.handleIntent(TaskManagerListIntent.Navigation(task.id))
                    }
                )
            }
        }

        if (tasks.loadState.append is LoadState.Loading) {
            item(
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.Padding16),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(
    searchQuery: String,
    selectedStatuses: Set<TaskPresentationStatus>,
    onSearchQueryChange: (String) -> Unit,
    onStatusToggle: (TaskPresentationStatus) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = { Text("Search tasks") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    }
                )
            },
            expanded = false,
            onExpandedChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .absoluteOffset(y = -Dimens.Padding16)
        ) {}

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.Padding8),
            horizontalArrangement = Arrangement.Center
        ) {
            TaskPresentationStatus.entries.forEach { status ->
                val selected = status in selectedStatuses
                FilterChip(
                    selected = selected,
                    onClick = { onStatusToggle(status) },
                    label = { Text(status.value) },
                    leadingIcon = {
                        Icon(
                            imageVector = status.icon,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}
