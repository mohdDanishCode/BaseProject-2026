package com.omniful.data.repository.notes

import com.omniful.data.repository.user.UserRepository
import com.omniful.database.dao.NoteDao
import com.omniful.database.dao.UserDao
import com.omniful.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesDataRepository @Inject constructor(
    val noteDao: NoteDao
): NotesRepository{
    private var nextRepository: NotesRepository? = null

    override fun getNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override suspend fun saveNote(note: NoteEntity): Long {
        return noteDao.insert(note)
    }

    override suspend fun deleteNotes(notes: List<NoteEntity>) {
        return noteDao.delete(notes)
    }

    override fun setNext(repository: NotesRepository): NotesRepository {
        nextRepository = repository
        return repository
    }

}


class UserDataRepository @Inject constructor(
    dao: UserDao
):UserRepository {
    private var nextRepository: UserRepository? = null

    override fun getName(): String? {
        return null ?:nextRepository?.getName()
    }

    override fun setNext(repository: UserRepository): UserRepository {
        nextRepository = repository
        return repository
    }
}