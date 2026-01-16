package com.omniful.app.presentation.notes.usecase

import com.omniful.data.repository.notes.NotesMainRepository
import com.omniful.database.model.NoteEntity
import javax.inject.Inject

data class NotesUseCases @Inject constructor(
    val getNotes: GetNotesUseCase,
    val saveNote: SaveNoteUseCase,
    val deleteNotes: DeleteNotesUseCase
)

class GetNotesUseCase @Inject constructor(
    val notesRepository : NotesMainRepository
) {
    operator fun invoke() = notesRepository.getNotes()
}

class SaveNoteUseCase @Inject constructor(
    val notesRepository : NotesMainRepository
) {
    suspend operator fun invoke(note: NoteEntity) =
        notesRepository.saveNote(note)
}

class DeleteNotesUseCase @Inject constructor(
    val notesRepository : NotesMainRepository
) {
    suspend operator fun invoke(notes: List<NoteEntity>) =
        notesRepository.deleteNotes(notes)
}