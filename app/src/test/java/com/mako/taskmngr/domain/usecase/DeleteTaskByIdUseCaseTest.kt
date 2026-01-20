package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.domain.repository.TasksRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DeleteTaskByIdUseCaseTest {

    private lateinit var useCase: DeleteTaskByIdUseCase
    private lateinit var repository: TasksRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = DeleteTaskByIdUseCase(repository)
    }

    @Test
    fun `invoke should call deleteTask with correct id`() = runTest {
        val args = DeleteTaskByIdUseCaseArgs(id = 123L)
        coEvery { repository.deleteTask(any()) } returns Unit

        useCase(args)

        val slot = io.mockk.slot<Long>()
        coVerify(exactly = 1) { repository.deleteTask(capture(slot)) }
        assertEquals(123L, slot.captured)
    }
}
