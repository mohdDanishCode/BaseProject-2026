package com.omniful.app.presentation.notes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omniful.database.model.NoteEntity

@Composable
fun NoteEntryScreen(
    note: NoteEntity?,
    onBack: () -> Unit,
    viewModel: NoteEntryViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf(note?.title.orEmpty()) }
    var desc by remember { mutableStateOf(note?.description.orEmpty()) }

    LaunchedEffect(Unit) {
        viewModel.init(note)
        viewModel.startAutoSave({ title }, { desc })
    }

    BackHandler {
        viewModel.save(title, desc)
        onBack()
    }

    Column(Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it })
        Spacer(Modifier.height(8.dp))
        TextField(value = desc, onValueChange = { desc = it })
    }
}
