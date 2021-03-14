package com.orgzly.android.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
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
        val view = inflater.inflate(R.layout.fragment_edit_table, container, false)

        val tableLayout = view.findViewById<TableLayout>(R.id.edit_table_table_layout)

        val tableRow1 = TableRow(context)
        val tv1 = TextView(context)
        tv1.setText("tv1 t")
        val tv2 = TextView(context)
        tv2.setText("tv2 t")
        tableRow1.addView(tv1)
        tableRow1.addView(tv2)
        tableLayout.addView(tableRow1)


        val tableRow2 = TableRow(context)
        val tv3 = TextView(context)
        tv3.setText("tv3 t")
        val tv4 = TextView(context)
        tv4.setText("tv4 t")
        tableRow2.addView(tv3)
        tableRow2.addView(tv4)
        tableLayout.addView(tableRow2)


        return view
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