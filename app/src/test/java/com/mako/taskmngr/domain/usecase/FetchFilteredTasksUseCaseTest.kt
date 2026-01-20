package com.mako.taskmngr.domain.usecase

import androidx.paging.PagingData
import com.mako.taskmngr.domain.entity.TaskDomainStatus
import com.mako.taskmngr.domain.entity.TaskFilterDomainEntity
import com.mako.taskmngr.domain.repository.TasksRepository
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchFilteredTasksUseCaseTest {

    private lateinit var useCase: FetchFilteredTasksUseCase
    private lateinit var repository: TasksRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = FetchFilteredTasksUseCase(repository)
    }

    @Test
    fun `invoke should call getAllTasks with correct filter`() = runTest {
        val args =
            FetchFilteredTasksUseCaseArgs(
                searchQuery = "query",
                selectedStatuses = setOf(TaskPresentationStatus.TODO)
            )

        every { repository.getAllTasks(any()) } returns flowOf(PagingData.empty())

        useCase(args).first()

        val slot = slot<TaskFilterDomainEntity>()
        verify(exactly = 1) { repository.getAllTasks(capture(slot)) }
        assertEquals("query", slot.captured.searchQuery)
        assertEquals(setOf(TaskDomainStatus.TODO), slot.captured.selectedStatuses)
    }

    @Test
    fun `invoke should use all statuses when selectedStatuses is empty`() = runTest {
        val args =
            FetchFilteredTasksUseCaseArgs(
                searchQuery = "",
                selectedStatuses = emptySet()
            )
        every { repository.getAllTasks(any()) } returns flowOf(PagingData.empty())

        useCase(args).first()

        val slot = slot<TaskFilterDomainEntity>()
        verify(exactly = 1) { repository.getAllTasks(capture(slot)) }
        assertEquals(TaskDomainStatus.entries.toSet(), slot.captured.selectedStatuses)
    }

    @Test
    fun `invoke should handle multiple selected statuses`() = runTest {
        val args =
            FetchFilteredTasksUseCaseArgs(
                searchQuery = "test",
                selectedStatuses =
                    setOf(
                        TaskPresentationStatus.TODO,
                        TaskPresentationStatus.IN_PROGRESS
                    )
            )
        every { repository.getAllTasks(any()) } returns flowOf(PagingData.empty())

        useCase(args).first()

        val slot = slot<TaskFilterDomainEntity>()
        verify(exactly = 1) { repository.getAllTasks(capture(slot)) }
        assertEquals(
            setOf(TaskDomainStatus.TODO, TaskDomainStatus.IN_PROGRESS),
            slot.captured.selectedStatuses
        )
        assertEquals("test", slot.captured.searchQuery)
    }
}
