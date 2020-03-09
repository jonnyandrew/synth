package com.flatmapdev.synth.keyboardShared

import androidx.annotation.StringRes
import com.flatmapdev.synth.R
import com.flatmapdev.synth.keyboardCore.model.Note

@StringRes
fun Note.toUiString() = when (this) {
    Note.C -> R.string.note_c
    Note.C_SHARP_D_FLAT -> R.string.note_c_sharp_d_flat
    Note.D -> R.string.note_d
    Note.D_SHARP_E_FLAT -> R.string.note_d_sharp_e_flat
    Note.E -> R.string.note_e
    Note.F -> R.string.note_f
    Note.F_SHARP_G_FLAT -> R.string.note_f_sharp_g_flat
    Note.G -> R.string.note_g
    Note.G_SHARP_A_FLAT -> R.string.note_g_sharp_a_flat
    Note.A -> R.string.note_a
    Note.A_SHARP_B_FLAT -> R.string.note_a_sharp_b_flat
    Note.B -> R.string.note_b
}
