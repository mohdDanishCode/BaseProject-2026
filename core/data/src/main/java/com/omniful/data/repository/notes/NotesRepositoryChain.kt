package com.omniful.data.repository.notes


import javax.inject.Inject


class NotesRepositoryChain @Inject constructor(
    notesDataRepository: NotesDataRepository,
) {

    private val repoList = listOf(notesDataRepository)

    fun getRepository(): NotesRepository{
        for (i in 0 until repoList.size - 1) {
            repoList[i].setNext(repoList[i + 1])
        }
        return repoList.first()
    }

}