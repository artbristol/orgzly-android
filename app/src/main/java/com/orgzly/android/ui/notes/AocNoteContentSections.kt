package com.orgzly.android.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.orgzly.R
import com.orgzly.android.ui.views.TextViewWithMarkup

class AocNoteContentSectionViewHolder(val view: View) : RecyclerView.ViewHolder(view)

private val ITEM_VIEW_TYPE_TEXT = 0
private val ITEM_VIEW_TYPE_TABLE = 1

class AocNoteContentSectionAdapter(val sections: List<AocNoteContent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = when {
        sections[position] is AocNoteContent.AocTableNoteContent -> ITEM_VIEW_TYPE_TABLE
        else -> ITEM_VIEW_TYPE_TEXT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // here we return either a TextView or a HorizontalScrollView, depending on whether it's a table or not
        when (viewType) {
            ITEM_VIEW_TYPE_TABLE -> {
                return AocNoteContentSectionViewHolder(
                        LayoutInflater.from(parent.context)
                                .inflate(R.layout.aoc_item_note_content_section_table, parent, false)
                )
            }
            ITEM_VIEW_TYPE_TEXT -> {
                return AocNoteContentSectionViewHolder(
                        LayoutInflater.from(parent.context)
                                .inflate(R.layout.aoc_item_note_content_section_text, parent, false)
                )
            }
            else -> {
                throw IllegalArgumentException("Unexpected viewType: [" + viewType + "]")
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = sections[position]

        when {
            item is AocNoteContent.AocTableNoteContent -> {
                holder.itemView.findViewById<TextView>(R.id.aoc_section_table_text).text = item.text
            }
            else -> {

                val textView = holder.itemView.findViewById<TextViewWithMarkup>(R.id.aoc_section_text)

                // TODO pass in context
//                if (AppPreferences.isFontMonospaced(context)) {
//                    textView.typeface = Typeface.MONOSPACE
//                }

                textView.setRawText(item.text)

                // TODO restore this
                /* If content changes (for example by toggling the checkbox), update the note. */
//                textView.onUserTextChangeListener = Runnable {
//                    if (textView.getRawText() != null) {
//                        val useCase = NoteUpdateContent(
//                                note.position.bookId,
//                                note.id,
//                                item.getRawText()?.toString())
//
//                        App.EXECUTORS.diskIO().execute {
//                            UseCaseRunner.run(useCase)
//                        }
//                    }
//                }

                // TODO restore this
//                ImageLoader.loadImages(holder.binding.itemHeadContent)

            }
        }

    }

    override fun getItemCount(): Int {
        return sections.size
    }

}