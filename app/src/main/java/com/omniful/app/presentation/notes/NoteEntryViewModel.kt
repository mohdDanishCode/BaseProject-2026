package com.omniful.app.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omniful.app.presentation.notes.usecase.NotesUseCases
import com.omniful.database.model.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEntryViewModel @Inject constructor(
    private val useCases: NotesUseCases
) : ViewModel() {

    private var noteId: Int? = null
    private var autoSaveJob: Job? = null

    fun init(note: NoteEntity?) {
        noteId = note?.id
    }

    fun startAutoSave(
        title: () -> String,
        desc: () -> String
    ) {
        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            while (isActive) {
                delay(5000)
                save(title(), desc())
            }
        }
    }

    fun save(title: String, desc: String) {
        if (title.isBlank() && desc.isBlank()) return

        viewModelScope.launch {
            val note = NoteEntity(
                id = noteId ?: 0,
                title = title,
                description = desc
            )
            val id = useCases.saveNote(note)
            if (noteId == null) noteId = id.toInt()
        }
    }

    override fun onCleared() {
        autoSaveJob?.cancel()
    }
}