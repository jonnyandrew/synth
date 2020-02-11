package com.flatmapdev.synth.engineData.adapter

import com.flatmapdev.synth.doubles.jni.FakeSynth
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NativeSynthEngineAdapterTest {
    @Test
    fun `it gets the version`() {
        val subject = NativeSynthEngineAdapter(
            FakeSynth(
                version = "1.2.3"
            )
        )

        val result = subject.getVersion()

        assertThat(result).isEqualTo("1.2.3")
    }
}