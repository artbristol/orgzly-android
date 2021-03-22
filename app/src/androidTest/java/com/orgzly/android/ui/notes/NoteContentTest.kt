package com.orgzly.android.ui.notes

import com.orgzly.android.ui.notes.NoteContent.TableNoteContent
import com.orgzly.android.ui.notes.NoteContent.TextNoteContent
import org.hamcrest.Matchers.emptyCollectionOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.Random

// TODO - check CRLF vs LF vs whatever MacOS does
class NoteContentTest {

    @Test
    fun emptyString() {
        val parse = NoteContent.parse("")
        assertThat(parse, (emptyCollectionOf(NoteContent.javaClass)))
    }

    @Test
    fun emptyLinesShouldStayInSingleSection() {
        checkExpected("\n\n", listOf(TextNoteContent("\n\n", 0, 1)))
    }

    @Test
    fun pipeInText() {
        checkExpected("""foo
|

foo|bar""", listOf(
                TextNoteContent("foo\n", 0, 3),
                TableNoteContent("|\n", 4, 5),
                TextNoteContent("\nfoo|bar", 6, 13)
        ))
    }

    @Test
    fun singleTable() {
        checkExpected("""|a|b|
|c|d|
""", listOf(TableNoteContent("""|a|b|
|c|d|
""", 0, 11)))
    }

    @Test
    fun singleTableNoFinalNewline() {
        checkExpected("""|a|b|
|c|d|""", listOf(TableNoteContent("""|a|b|
|c|d|""", 0, 10)))
    }

    @Test
    fun singleLineTextTableText() {
        checkExpected("""foo
|
bar""", listOf(
                TextNoteContent("foo\n", 0, 3),
                TableNoteContent("|\n", 4, 5),
                TextNoteContent("bar", 6, 8)
        ))
    }


    @Test
    fun blankLineTextTableText() {
        checkExpected("""
|
bar
""", listOf(
                TextNoteContent("\n", 0, 0),
                TableNoteContent("|\n", 1, 2),
                TextNoteContent("bar\n", 3, 6)
        ))
    }

    @Test
    fun tableBlankLineTable() {
        checkExpected("""|zoo|

|zog|""", listOf(
                TableNoteContent("|zoo|\n",0,5),
                TextNoteContent("\n",6,6),
                TableNoteContent("|zog|",7,11)
        ))
    }

    @Test
    fun textTableBlankLineText() {
        checkExpected("""foo
|

chops""", listOf(
                TextNoteContent("foo\n",0,3),
                TableNoteContent("|\n",4,5),
                TextNoteContent("\nchops",6,11)
        ))
    }


    @Test
    fun textTableTextTableText() {
        checkExpected("""text1
|table2a|
|table2b|
text3a
text3b
text3c
|table4|
text5
""", listOf(
                TextNoteContent("text1\n",0,5),
                TableNoteContent("|table2a|\n|table2b|\n",6,25),
                TextNoteContent("text3a\ntext3b\ntext3c\n",26,46),
                TableNoteContent("|table4|\n",47,55),
                TextNoteContent("text5\n",56,61)
        ))
    }

    @Test
    fun randomStringsRoundTrip() {

        val stringAtoms: List<String> = listOf("\n", "a", "|")

        for (i in 0..1000) {
            val rawStringLength = Random().nextInt(100)
            val builder = StringBuilder()
            for (j in 0..rawStringLength) {
                builder.append(stringAtoms.random())
            }

            val raw = builder.toString()

            val actual: List<NoteContent> = NoteContent.parse(raw)

            val roundTripped: String = actual.fold("") { acc: String, current: NoteContent -> acc + current.text }

            assertEquals(raw, roundTripped)

        }

    }


    private fun checkExpected(input: String, expected: List<NoteContent>) {
        val actual: List<NoteContent> = NoteContent.parse(input)
        assertEquals(expected, actual)

        val roundTripped: String = actual.fold("") { acc: String, current: NoteContent -> acc + current.text }

        assertEquals(input, roundTripped)

        actual.forEach {
            assertEquals(it.text, input.substring(it.startOffset, it.endOffset + 1))
        }
    }
}
