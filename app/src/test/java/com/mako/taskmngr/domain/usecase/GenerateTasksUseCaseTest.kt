package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.domain.entity.NewTaskDomainEntity
import com.mako.taskmngr.domain.repository.TasksRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GenerateTasksUseCaseTest {

    private lateinit var useCase: GenerateTasksUseCase
    private lateinit var repository: TasksRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = GenerateTasksUseCase(repository)
    }

    @Test
    fun `invoke should generate 100 tasks and call saveTasks`() = runTest {
        coEvery { repository.saveTasks(any()) } returns Unit

        useCase()

        val slot = slot<List<NewTaskDomainEntity>>()
        coVerify(exactly = 1) { repository.saveTasks(capture(slot)) }
        assertEquals(100, slot.captured.size)
        assert(slot.captured[0].title.startsWith("Task #"))
    }
}
