package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.domain.entity.NewTaskDomainEntity
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

class SaveNewTaskByIdUseCaseTest {

    private lateinit var useCase: SaveNewTaskByIdUseCase
    private lateinit var repository: TasksRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = SaveNewTaskByIdUseCase(repository)
    }

    @Test
    fun `invoke should call saveTask with correct mapped domain entity`() = runTest {
        val args =
            SaveNewTaskByIdUseCaseArgs(
                title = "Test Title",
                description = "Test Description",
                status = TaskPresentationStatus.TODO
            )
        coEvery { repository.saveTask(any()) } returns Unit

        useCase(args)

        val slot = slot<NewTaskDomainEntity>()
        coVerify(exactly = 1) { repository.saveTask(capture(slot)) }
        assertEquals("Test Title", slot.captured.title)
        assertEquals("Test Description", slot.captured.description)
        assertEquals(TaskDomainStatus.TODO, slot.captured.status)
    }
}
