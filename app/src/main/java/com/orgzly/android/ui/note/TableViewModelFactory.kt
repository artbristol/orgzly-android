package com.orgzly.android.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orgzly.android.data.DataRepository
import com.orgzly.android.ui.Place

class TableViewModelFactory(
        private val dataRepository: DataRepository,
        private val bookId: Long,
        private val noteId: Long,
        private val tableStartOffset: Int,
        private val tableEndOffset: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TableViewModel(dataRepository, bookId, noteId, tableStartOffset, tableEndOffset) as T
    }

    companion object {
        @JvmStatic
        fun getInstance(
                dataRepository: DataRepository,
                bookId: Long,
                noteId: Long,
                tableStartOffset: Int,
                tableEndOffset: Int

        ): ViewModelProvider.Factory {

            return TableViewModelFactory(dataRepository, bookId, noteId, tableStartOffset, tableEndOffset)
        }
    }
}