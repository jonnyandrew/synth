package com.flatmapdev.synth.engineData.mapper

import com.flatmapdev.synth.engineCore.model.AmpEnvelopeConfig.ATTACK_MAX_MS
import com.flatmapdev.synth.engineCore.model.AmpEnvelopeConfig.DECAY_MAX_MS
import com.flatmapdev.synth.engineCore.model.AmpEnvelopeConfig.RELEASE_MAX_MS
import com.flatmapdev.synth.engineCore.model.Envelope
import java.lang.IndexOutOfBoundsException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EnvelopeFloatArrayMapperTest {
    @Test
    fun `it maps an Envelope to a FloatArray`() {
        val envelope = Envelope(
            attackPercent = 10,
            decayPercent = 20,
            sustainPercent = 30,
            releasePercent = 40
        )

        val result = envelope.toFloatArray()

        assertThat(result).isEqualTo(
            floatArrayOf(
                0.1f * ATTACK_MAX_MS,
                0.2f * DECAY_MAX_MS,
                0.3f,
                0.4f * RELEASE_MAX_MS
            )
        )
    }

    @Test
    fun `it maps a FloatArray to an Envelope`() {
        val floatArrayAdsr = floatArrayOf(
            0.1f * ATTACK_MAX_MS,
            0.2f * DECAY_MAX_MS,
            0.3f,
            0.4f * RELEASE_MAX_MS
        )

        val result = floatArrayAdsr.toEnvelope()

        assertThat(result).isEqualTo(
            Envelope(
                attackPercent = 10,
                decayPercent = 20,
                sustainPercent = 30,
                releasePercent = 40
            )
        )
    }

    @Test
    fun `when a FloatArray has more elements, it maps a FloatArray to an Envelope`() {
        val floatArrayAdsr = floatArrayOf(
            0.1f * ATTACK_MAX_MS,
            0.2f * DECAY_MAX_MS,
            0.3f,
            0.4f * RELEASE_MAX_MS,
            12345f // Extra element
        )

        val result = floatArrayAdsr.toEnvelope()

        assertThat(result).isEqualTo(
            Envelope(
                attackPercent = 10,
                decayPercent = 20,
                sustainPercent = 30,
                releasePercent = 40
            )
        )
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `when a FloatArray has less elements, it throws`() {
        val floatArrayAdsr = floatArrayOf(
            0.1f * ATTACK_MAX_MS,
            0.2f * DECAY_MAX_MS,
            0.3f
            // missing release
        )

        val result = floatArrayAdsr.toEnvelope()
    }
}
