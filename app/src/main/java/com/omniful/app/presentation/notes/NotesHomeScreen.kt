package com.omniful.app.presentation.notes

import android.R.attr.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omniful.database.model.NoteEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesHomeScreen(
    onAdd: () -> Unit,
    onNoteClick: (NoteEntity) -> Unit,
    viewModel: NotesHomeViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsStateWithLifecycle()
    val selected = remember { mutableStateListOf<NoteEntity>() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, null)
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
                actions = {
                    if (selected.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.deleteNotes(selected)
                            selected.clear()
                        }) {
                            Icon(Icons.Default.Delete, null)
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(notes) { note ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onNoteClick(note) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selected.contains(note),
                        onCheckedChange = {
                            if (it) selected.add(note)
                            else selected.remove(note)
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(note.title)
                }
            }
        }
    }

}
