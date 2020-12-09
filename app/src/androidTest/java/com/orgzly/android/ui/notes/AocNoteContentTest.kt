package com.orgzly.android.ui.notes

import com.orgzly.android.ui.notes.AocNoteContent.AocTableNoteContent
import com.orgzly.android.ui.notes.AocNoteContent.AocTextNoteContent
import org.hamcrest.Matchers.emptyCollectionOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import java.lang.StringBuilder
import java.util.*
import java.util.stream.IntStream

// TODO - check CRLF vs LF vs whatever MacOS does
class AocNoteContentTest {


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

            val actual: List<AocNoteContent> = AocNoteContent.parse(raw)

            val roundTripped: String = actual.fold("") { acc: String, current: AocNoteContent -> acc + current.text }

            assertEquals(raw, roundTripped)

        }

    }


    @Test
    fun emptyString() {
        val parse = AocNoteContent.parse("")
        assertThat(parse, (emptyCollectionOf(AocNoteContent.javaClass)))
    }

    @Test
    fun emptyLinesShouldStayInSingleSection() {
        checkExpected("\n\n", listOf(AocTextNoteContent("\n\n")))
    }

    @Test
    fun singleTable() {
        checkExpected("""|a|b|
|c|d|
""", listOf(AocTableNoteContent("""|a|b|
|c|d|
""")))
    }

    @Test
    fun singleTableNoFinalNewline() {
        checkExpected("""|a|b|
|c|d|""", listOf(AocTableNoteContent("""|a|b|
|c|d|""")))
    }

    @Test
    fun singleLineTextTableText() {
        checkExpected("""foo
|
bar""", listOf(
                AocTextNoteContent("foo\n"),
                AocTableNoteContent("|\n"),
                AocTextNoteContent("bar")
        ))
    }


    @Test
    fun blankLineTextTableText() {
        checkExpected("""
|
bar
""", listOf(
                AocTextNoteContent("\n"),
                AocTableNoteContent("|\n"),
                AocTextNoteContent("bar\n")
        ))
    }

    @Test
    fun tableBlankLineTable() {
        checkExpected("""|zoo|

|zog|""", listOf(
                AocTableNoteContent("|zoo|\n"),
                AocTextNoteContent("\n"),
                AocTableNoteContent("|zog|")
        ))
    }

    @Test
    fun textTableBlankLineText() {
        checkExpected("""foo
|

chops""", listOf(
                AocTextNoteContent("foo\n"),
                AocTableNoteContent("|\n"),
                AocTextNoteContent("\nchops")
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
                AocTextNoteContent("text1\n"),
                AocTableNoteContent("|table2a|\n|table2b|\n"),
                AocTextNoteContent("text3a\ntext3b\ntext3c\n"),
                AocTableNoteContent("|table4|\n"),
                AocTextNoteContent("text5\n")
        ))
    }


    private fun checkExpected(input: String, expected: List<AocNoteContent>) {
        val actual: List<AocNoteContent> = AocNoteContent.parse(input)
        assertEquals(expected, actual)

        val roundTripped: String = actual.fold("") { acc: String, current: AocNoteContent -> acc + current.text }

        assertEquals(input, roundTripped)

    }
}
