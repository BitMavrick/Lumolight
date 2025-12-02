package com.bitmavrick.data.repository

import com.bitmavrick.data.domain.model.ScreenDuration
import com.bitmavrick.data.domain.repository.ScreenDurationRepository
import com.bitmavrick.data.local.dao.ScreenDurationDao
import com.bitmavrick.data.local.entity.ScreenDurationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScreenDurationRepositoryImpl @Inject constructor(
    private val dao: ScreenDurationDao
) : ScreenDurationRepository {
    override fun getAllScreenDurations(): Flow<List<ScreenDuration>> {
        return dao.getAllScreenDurations().map { list ->
            list.map {
                ScreenDuration(
                    id = it.id,
                    duration = it.duration,
                    orderIndex = it.orderIndex
                )
            }
        }
    }

    override suspend fun insertScreenDuration(screenDuration: ScreenDuration) {
        dao.insertScreenDuration(
            ScreenDurationEntity(
                id = screenDuration.id,
                duration = screenDuration.duration,
                orderIndex = screenDuration.orderIndex
            )
        )
    }

    override suspend fun updateScreenDuration(screenDuration: ScreenDuration) {
        dao.updateScreenDuration(
            ScreenDurationEntity(
                id = screenDuration.id,
                duration = screenDuration.duration,
                orderIndex = screenDuration.orderIndex
            )
        )
    }

    override suspend fun reorder(newList: List<ScreenDuration>) {
        val reorderedEntities = newList.mapIndexed { index, duration ->
            ScreenDurationEntity(
                id = duration.id,
                duration = duration.duration,
                orderIndex = index
            )
        }

        dao.reorderScreenDurations(reorderedEntities)
    }

    override suspend fun deleteScreenDuration(screenDuration: ScreenDuration) {
        dao.deleteScreenDuration(
            ScreenDurationEntity(
                id = screenDuration.id,
                duration = screenDuration.duration,
                orderIndex = screenDuration.orderIndex
            )
        )
    }
}