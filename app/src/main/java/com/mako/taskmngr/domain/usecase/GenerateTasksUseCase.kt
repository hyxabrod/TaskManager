package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.core.VoidUseCaseWithNoArgs
import com.mako.taskmngr.domain.entity.NewTaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskDomainStatus
import com.mako.taskmngr.domain.repository.TasksRepository
import javax.inject.Inject
import kotlin.random.Random

class GenerateTasksUseCase @Inject constructor(private val repository: TasksRepository) :
    VoidUseCaseWithNoArgs {

    override suspend fun invoke() {
        val statuses = TaskDomainStatus.entries.toTypedArray()
        val descriptions =
            listOf(
                "Complete the pending report",
                "Review the PR changes",
                "Update the documentation",
                "Prepare for the meeting",
                "Fix the reported bug",
                "Refactor the legacy code",
                "Write unit tests",
                "Deploy to staging",
                "Sync with the design team",
                "Check analytics data",
                "Research competitor features",
                "Optimize database queries",
                "Update dependency versions",
                "Draft release notes",
                "Conduct user interviews",
                "Design new icon set",
                "Investigate crash reports",
                "Set up CI/CD pipeline",
                "Clean up temporary files",
                "Archive old projects"
            )

        val newTasks =
            (1..100).map { index ->
                val status = statuses[Random.nextInt(statuses.size)]
                val description = descriptions[Random.nextInt(descriptions.size)]
                val title = "Task #$index"

                NewTaskDomainEntity(
                    title = title,
                    description = description,
                    status = status
                )
            }
        repository.saveTasks(newTasks)
    }
}
