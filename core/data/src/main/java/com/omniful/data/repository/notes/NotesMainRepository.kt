package com.omniful.data.repository.notes


import com.omniful.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesMainRepository  @Inject constructor(
    private val notesRepositoryChain: NotesRepositoryChain
) {
    private var userRepository: NotesRepository = notesRepositoryChain.getRepository()

     fun getNotes(): Flow<List<NoteEntity>> =
        userRepository.getNotes()

     suspend fun saveNote(note: NoteEntity): Long =
        userRepository.saveNote(note)

     suspend fun deleteNotes(notes: List<NoteEntity>) =
        userRepository.deleteNotes(notes)


}
