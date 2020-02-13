package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetKeyboard @Inject constructor() {
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
}