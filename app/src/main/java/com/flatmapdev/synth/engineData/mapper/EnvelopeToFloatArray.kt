package com.flatmapdev.synth.engineData.mapper

import com.flatmapdev.synth.engineCore.model.Envelope

private const val MAX_ATTACK_MS = 5000
private const val MAX_DECAY_MS = 3000
private const val MAX_RELEASE_MS = 5000

fun Envelope.toFloatArray(): FloatArray {
    return floatArrayOf(
        attackPercent * 0.01f * MAX_ATTACK_MS,
        decayPercent * 0.01f * MAX_DECAY_MS,
        sustainPercent * 0.01f,
        releasePercent * 0.01f * MAX_RELEASE_MS
    )
}

fun FloatArray.toEnvelope(): Envelope {
    val (attackTimeMs, decayTimeMs, sustainLevel, releaseTimeMs)
            = this
    return Envelope(
        (attackTimeMs / MAX_ATTACK_MS * 100).toInt(),
        (decayTimeMs / MAX_DECAY_MS * 100).toInt(),
        (sustainLevel * 100).toInt(),
        (releaseTimeMs / MAX_RELEASE_MS * 100).toInt()
    )
}
