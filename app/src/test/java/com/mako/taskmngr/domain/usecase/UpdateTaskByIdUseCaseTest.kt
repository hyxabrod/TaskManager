package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.domain.entity.TaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskDomainStatus
import com.mako.taskmngr.domain.repository.TasksRepository
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UpdateTaskByIdUseCaseTest {

    private lateinit var useCase: UpdateTaskByIdUseCase
    private lateinit var repository: TasksRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpdateTaskByIdUseCase(repository)
    }

    @Test
    fun `invoke should call updateTask with correct mapped domain entity`() = runTest {
        val args =
            UpdateTaskByIdUseCaseArgs(
                id = 1L,
                title = "Updated Title",
                description = "Updated Description",
                status = TaskPresentationStatus.DONE
            )
        coEvery { repository.updateTask(any()) } returns Unit

        useCase(args)

        val slot = slot<TaskDomainEntity>()
        coVerify(exactly = 1) { repository.updateTask(capture(slot)) }
        assertEquals(1L, slot.captured.id)
        assertEquals("Updated Title", slot.captured.title)
        assertEquals("Updated Description", slot.captured.description)
        assertEquals(TaskDomainStatus.DONE, slot.captured.status)
    }
}
