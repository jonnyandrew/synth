package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetKeyboard @Inject constructor(
    private val getScale: GetScale
) {
    fun execute(): List<Key> {
        return listOf(
            Key(Note.C, 3),
            Key(Note.D, 3),
            Key(Note.E, 3),
            Key(Note.F, 3),
            Key(Note.G, 3),
            Key(Note.A, 3),
            Key(Note.B, 3),
            Key(Note.C, 4),
            Key(Note.D, 4),
            Key(Note.E, 4),
            Key(Note.F, 4),
            Key(Note.G, 4),
            Key(Note.A, 4),
            Key(Note.B, 4),
            Key(Note.C, 5)
        )
    }

//    private fun execute2(): List<Key> {
//        val scale = getScale.execute()
//        val scaleNoteIndices = scale.getNoteIndicies()
//        val octaveRange = 4..6
//        val tonicNoteIndex =
//            Note.values().indexOf(scale.tonic)
//        val notes = Note.values()
//            .toMutableList()
//            .apply {
//                Collections.rotate(this, tonicNoteIndex)
//            }
//            .toList()
//            .filterIndexed { index, _ ->
//                scaleNoteIndices.contains(index)
//            }
//
//        return octaveRange.flatMap { octave ->
//            notes.mapIndexed { index, note ->
//                Key(note, if (index < tonicNoteIndex) octave + 1 else octave)
//            }
//        }
//    }
//
//    private fun Scale.getNoteIndicies(): Array<Int> {
//        return when (type) {
//            ScaleType.Minor -> arrayOf(0, 2, 3, 5, 7, 8, 10)
//            ScaleType.Major -> arrayOf(0, 2, 4, 5, 7, 9, 11)
//            ScaleType.Pentatonic -> arrayOf(0, 3, 5, 7, 10)
//        }
//    }
}
