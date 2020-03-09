package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@Reusable
class GetKeyboard @Inject constructor(
    private val getScale: GetScale
) {
    fun execute(): Flow<List<Key>> {
        return getScale.execute()
            .map { scale ->
                val scaleNoteIndices = scale.getNoteIndicies()
                val tonicNoteIndex = Note.values().indexOf(scale.tonic)

                // create an ordered list of notes in the scale
                // starting at the tonic note
                val notes = Note.values()
                    .toMutableList()
                    .apply {
                        Collections.rotate(this, -tonicNoteIndex)
                    }
                    .filterIndexed { index, _ ->
                        scaleNoteIndices.contains(index)
                    }
                    .toList()

                // repeat the notes over the octave range
                val octaveRange = 3..4
                val scaleOverOctaves = octaveRange.flatMap { octave ->
                    notes.map { note ->
                        Key(note, if (note.ordinal < tonicNoteIndex) octave + 1 else octave)
                    }
                }

                // add a final tonic note at the top of the scale
                scaleOverOctaves
                    .toMutableList()
                    .apply {
                        add(Key(scale.tonic, octaveRange.last + 1))
                    }
            }
    }

    private fun Scale.getNoteIndicies(): Array<Int> {
        return when (type) {
            ScaleType.HarmonicMinor -> arrayOf(0, 2, 3, 5, 7, 8, 11)
            ScaleType.Major -> arrayOf(0, 2, 4, 5, 7, 9, 11)
            ScaleType.MinorPentatonic -> arrayOf(0, 3, 5, 7, 10)
        }
    }
}
