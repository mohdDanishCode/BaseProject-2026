package com.omniful.app.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omniful.app.presentation.notes.usecase.NotesUseCases
import com.omniful.data.repository.notes.NotesMainRepository
import com.omniful.data.repository.notes.NotesRepository
import com.omniful.data.repository.user.UserMainRepository
import com.omniful.database.model.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesHomeViewModel@Inject constructor(
    private val useCases: NotesUseCases
) : ViewModel() {
    val notes = useCases.getNotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun deleteNotes(notes: List<NoteEntity>) {
        viewModelScope.launch {
            useCases.deleteNotes(notes)
        }
    }
}