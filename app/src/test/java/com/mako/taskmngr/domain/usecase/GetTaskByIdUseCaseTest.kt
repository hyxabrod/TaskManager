package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.domain.entity.TaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskDomainStatus
import com.mako.taskmngr.domain.repository.TasksRepository
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTaskByIdUseCaseTest {

    private lateinit var useCase: GetTaskByIdUseCase
    private lateinit var repository: TasksRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTaskByIdUseCase(repository)
    }

    @Test
    fun `invoke should call getTaskById and return mapped presentation entity`() = runTest {
        val args = GetTaskByIdUseCaseArgs(id = 1L)
        val domainEntity =
            TaskDomainEntity(
                id = 1L,
                title = "Title",
                description = "Description",
                status = TaskDomainStatus.TODO
            )
        coEvery { repository.getTaskById(any()) } returns domainEntity

        val result = useCase(args)

        val slot = io.mockk.slot<Long>()
        coVerify(exactly = 1) { repository.getTaskById(capture(slot)) }
        assertEquals(1L, slot.captured)

        assertEquals(1L, result.id)
        assertEquals("Title", result.title)
        assertEquals("Description", result.description)
        assertEquals(TaskPresentationStatus.TODO, result.status)
    }
}
