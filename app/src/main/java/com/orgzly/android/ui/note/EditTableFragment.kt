package com.orgzly.android.ui.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.orgzly.android.App
import com.orgzly.android.data.DataRepository
import com.orgzly.databinding.FragmentEditTableBinding
import javax.inject.Inject

class EditTableFragment : Fragment() {

    private lateinit var binding: FragmentEditTableBinding

    @Inject
    internal lateinit var dataRepository: DataRepository

    private lateinit var viewModel: TableViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)

        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var args = requireNotNull(arguments)

        val factory = TableViewModelFactory.getInstance(
                dataRepository,
                args.getLong(ARG_BOOK_ID),
                args.getLong(ARG_NOTE_ID),
                args.getInt(ARG_TABLE_START_OFFSET),
                args.getInt(ARG_TABLE_END_OFFSET))

        viewModel = ViewModelProviders.of(this, factory).get(TableViewModel::class.java)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentEditTableBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {

        private const val ARG_BOOK_ID = "book_id"
        private const val ARG_NOTE_ID = "note_id"
        private const val ARG_TABLE_START_OFFSET = "table_start_offset"
        private const val ARG_TABLE_END_OFFSET = "table_end_offset"


        @JvmStatic
        fun newInstance(bookId: Long,
                        noteId: Long,
                        tableStartOffset: Int,
                        tableEndOffset: Int) =
                EditTableFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_BOOK_ID, bookId)
                        putLong(ARG_NOTE_ID, noteId)
                        putInt(ARG_TABLE_START_OFFSET, tableStartOffset)
                        putInt(ARG_TABLE_END_OFFSET, tableEndOffset)
                    }
                }
    }
}