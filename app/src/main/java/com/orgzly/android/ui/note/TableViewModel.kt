package com.orgzly.android.ui.note

import androidx.lifecycle.MutableLiveData
import com.orgzly.BuildConfig
import com.orgzly.android.App
import com.orgzly.android.data.DataRepository
import com.orgzly.android.db.entity.BookView
import com.orgzly.android.db.entity.NoteView
import com.orgzly.android.ui.CommonViewModel
import com.orgzly.android.util.LogUtils

class TableViewModel(
        private val dataRepository: DataRepository,
        var bookId: Long,
        private var noteId: Long,
        private val tableStartOffset: Int,
        private val tableEndOffset: Int
) : CommonViewModel() {

    private val TAG = TableViewModel::class.java.name

    val bookView: MutableLiveData<BookView> = MutableLiveData()

    val noteView: MutableLiveData<NoteView> = MutableLiveData()

    val tableView: MutableLiveData<String> = MutableLiveData("a cund")

    var notePayload: NotePayload? = null

    fun loadData() {
        App.EXECUTORS.diskIO().execute {
            val book = dataRepository.getBookView(bookId)

            val nullableNote = dataRepository.getNoteView(noteId)

            notePayload =
                dataRepository.getNotePayload(noteId)


            bookView.postValue(book)

            noteView.postValue(nullableNote)

            LogUtils.d(TAG, noteId, bookId, tableStartOffset, tableEndOffset)

            val note = nullableNote!!

            val content = note.note.content!!

            val tableText = content.substring(tableStartOffset, tableEndOffset)

            tableView.postValue(tableText)

            if (BuildConfig.LOG_DEBUG) LogUtils.d(TAG, "loaded data: " + tableText)

        }
    }

}
