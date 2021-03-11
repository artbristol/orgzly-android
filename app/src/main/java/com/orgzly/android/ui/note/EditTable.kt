package com.orgzly.android.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orgzly.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TABLETEXT = "tableText"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditTable.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTable : Fragment() {
    // TODO: Rename and change types of parameters
    private var tableText: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tableText = it.getString(ARG_TABLETEXT)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_table, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param tableText Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditTable.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(tableText: String, param2: String) =
                EditTable().apply {
                    arguments = Bundle().apply {
                        putString(ARG_TABLETEXT, tableText)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}