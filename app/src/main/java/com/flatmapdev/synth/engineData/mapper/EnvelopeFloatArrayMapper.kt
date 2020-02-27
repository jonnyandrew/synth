package com.flatmapdev.synth.engineData.mapper

import com.flatmapdev.synth.engineCore.model.AmpEnvelopeConfig.ATTACK_MAX_MS
import com.flatmapdev.synth.engineCore.model.AmpEnvelopeConfig.DECAY_MAX_MS
import com.flatmapdev.synth.engineCore.model.AmpEnvelopeConfig.RELEASE_MAX_MS
import com.flatmapdev.synth.engineCore.model.Envelope

fun Envelope.toFloatArray(): FloatArray {
    return floatArrayOf(
        (attackPercent.toFloat() / 100) * ATTACK_MAX_MS,
        (decayPercent.toFloat() / 100) * DECAY_MAX_MS,
        sustainPercent.toFloat() / 100,
        (releasePercent.toFloat() / 100) * RELEASE_MAX_MS
    )
}

fun FloatArray.toEnvelope(): Envelope {
    val (attackTimeMs, decayTimeMs, sustainLevel, releaseTimeMs) =
            this
    return Envelope(
        (attackTimeMs / ATTACK_MAX_MS * 100).toInt(),
        (decayTimeMs / DECAY_MAX_MS * 100).toInt(),
        (sustainLevel * 100).toInt(),
        (releaseTimeMs / RELEASE_MAX_MS * 100).toInt()
    )
}
