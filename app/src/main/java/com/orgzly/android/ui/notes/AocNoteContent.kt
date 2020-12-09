package com.orgzly.android.ui.notes

sealed class AocNoteContent {

    abstract val text: String

    data class AocTextNoteContent(override val text: String) : AocNoteContent() {
    }

    data class AocTableNoteContent(override val text: String) : AocNoteContent() {

        fun reformat() {
            // placeholder - but would fix all the spacing, missing cells, etc. Complicated
        }
    }


    companion object {

        private fun lineIsTable(raw: String) = raw.length > 0 && raw.get(0) == '|'

        /**
         * Converts the provided raw string  (with embedded newlines) into a list of sections of
         * either text or tables. Each section is contiguous and can contain newlines.
         *
         * This is horrible, never try to write your own parser. Consider using a regex instead.
         */
        fun parse(raw: String): List<AocNoteContent> {
            val list: MutableList<AocNoteContent> = mutableListOf()

            var currentText = ""
            var currentTable = ""

            var previousIsTable: Boolean = this.lineIsTable(raw)

            val rawSplitByNewlines = raw.split("\n")

            val missingLastNewline = rawSplitByNewlines.last() != ""

            val linesForParsing =
                    if (missingLastNewline) {
                        rawSplitByNewlines
                    } else {
                        rawSplitByNewlines.dropLast(1)
                    }

            linesForParsing.forEach {
                val currentIsTable = lineIsTable(it)
                when {
                    currentIsTable && previousIsTable -> {
                        currentTable += it + "\n"
                    }
                    currentIsTable && !previousIsTable -> {
                        currentTable = it + "\n"
                        list.add(AocTextNoteContent(currentText))
                        currentText = ""
                    }
                    !currentIsTable && previousIsTable -> {
                        currentText = it + "\n"
                        list.add(AocTableNoteContent(currentTable))
                        currentTable = ""
                    }
                    !currentIsTable && !previousIsTable -> {
                        currentText += it + "\n"
                    }
                }
                previousIsTable = currentIsTable
            }

            if (linesForParsing.isNotEmpty()) {
                if (previousIsTable) {
                    list.add(AocTableNoteContent(if (missingLastNewline) {
                        currentTable.dropLast(1)
                    } else currentTable))
                } else {
                    list.add(AocTextNoteContent(if (missingLastNewline) {
                        currentText.dropLast(1)
                    } else currentText))
                }
            }

            return list
        }


    }

}



