package com.omniful.data.repository.notes

import com.omniful.data.repository.user.UserRepository
import com.omniful.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<NoteEntity>>

    suspend fun saveNote(note: NoteEntity): Long

    suspend fun deleteNotes(notes: List<NoteEntity>)

    fun setNext(repository: NotesRepository): NotesRepository

}