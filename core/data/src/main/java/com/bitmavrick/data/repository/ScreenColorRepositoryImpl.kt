package com.bitmavrick.data.repository

import com.bitmavrick.data.local.dao.ScreenColorDao
import com.bitmavrick.data.local.entity.ScreenColorEntity
import com.bitmavrick.data.domain.model.ScreenColor
import com.bitmavrick.data.domain.repository.ScreenColorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScreenColorRepositoryImpl @Inject constructor (
    private val dao: ScreenColorDao
) : ScreenColorRepository {
    override fun getAllScreenColors(): Flow<List<ScreenColor>> {
        return dao.getAllScreenColors().map { list->
            list.map {
                ScreenColor(
                    id = it.id,
                    title = it.title,
                    colorCode = it.colorCode,
                    orderIndex = it.orderIndex
                )
            }
        }
    }

    override suspend fun insertColor(color: ScreenColor) {
        dao.insertColor(
            ScreenColorEntity(
                id = color.id,
                title = color.title,
                colorCode = color.colorCode,
                orderIndex = color.orderIndex
            )
        )
    }

    override suspend fun updateColor(color: ScreenColor) {
        dao.updateColor(
            ScreenColorEntity(
                id = color.id,
                title = color.title,
                colorCode = color.colorCode
            )
        )
    }

    override suspend fun reorder(newList: List<ScreenColor>) {
        val reorderedEntities = newList.mapIndexed { index, color ->
            ScreenColorEntity(
                id = color.id,
                title = color.title,
                colorCode = color.colorCode,
                orderIndex = index
            )
        }

        dao.reorderColors(reorderedEntities)
    }

    override suspend fun deleteColor(color: ScreenColor) {
        dao.deleteColor(
            ScreenColorEntity(
                id = color.id,
                title = color.title,
                colorCode = color.colorCode
            )
        )
    }
}