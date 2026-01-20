package com.mako.taskmngr.core

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface UseCase

interface UseCaseNoArgs : UseCase {
    suspend operator fun invoke(): PresentationEntity
}

interface UseCaseWithArgs<A : UseCaseWithArgs.Args> : UseCase {
    suspend operator fun invoke(args: A): PresentationEntity
    abstract class Args
}

interface PagedFlowUseCaseWithNoArgs : UseCase {
    operator fun invoke(): Flow<PagingData<out PresentationEntity>>
}

interface PagedFlowUseCaseWithArgs<A : PagedFlowUseCaseWithArgs.Args> : UseCase {
    operator fun invoke(args: A): Flow<PagingData<out PresentationEntity>>
    abstract class Args
}

interface ListUseCaseWithArgs<A : ListUseCaseWithArgs.Args> : UseCase {
    suspend operator fun invoke(args: A): List<PresentationEntity>
    abstract class Args
}

interface VoidUseCaseWithArgs<A : VoidUseCaseWithArgs.Args> : UseCase {
    suspend operator fun invoke(args: A)
    abstract class Args
}

interface VoidUseCaseWithNoArgs : UseCase {
    suspend operator fun invoke()
}
