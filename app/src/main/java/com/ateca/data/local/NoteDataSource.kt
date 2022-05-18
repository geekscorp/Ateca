package com.ateca.data.local

import androidx.room.withTransaction
import com.ateca.data.local.room.NoteDatabase
import com.ateca.data.local.room.dao.NoteDao
import com.ateca.data.local.room.model.RoomTag
import com.ateca.data.local.room.toEntity
import com.ateca.data.local.room.toModel
import com.ateca.domain.datasource.INoteDataSource
import com.ateca.domain.models.Note
import com.ateca.domain.models.NoteId

/**
 * Created by dronpascal on 16.05.2022.
 */
class NoteDataSource(
    private val noteDao: NoteDao,
    private val noteDatabase: NoteDatabase
) : INoteDataSource {

    override suspend fun selectAll(): List<Note> =
        noteDao.selectAll().toModel()

    override suspend fun select(id: NoteId): Note =
        noteDao.select(id).toModel()

    override suspend fun setArchived(id: NoteId, isArchived: Boolean) {
        noteDao.setArchived(id, isArchived)
    }

    override suspend fun saveNote(note: Note) {
        noteDatabase.withTransaction {
            noteDao.deleteByNoteId(note.id)
            noteDao.insert(note.toEntity())
            val links = note.links.toEntity()
            noteDatabase.linkDao.insert(*links.toTypedArray())
            val tags = note.tags.map { RoomTag(note.id, it) }
            noteDatabase.tagDao.insert(*tags.toTypedArray())
        }
    }

    override suspend fun selectBaseTitles() =
        noteDao.selectBaseTitles()

    override suspend fun getIdByTitle(title: String): NoteId =
        noteDao.selectIdByTitle(title)

    override suspend fun deleteNoteById(id: NoteId) {
        noteDao.deleteByNoteId(id)
    }
}
