package com.flatmapdev.synth.keyboardData.mapper

import org.junit.Test

class ScaleTypePrimitiveMapperTest {
    @Test(expected = IllegalArgumentException::class)
    fun `when scale type String is not recognized it throws`() {
        parseScaleTypeFromSharedPreferencesString("not a scale type")
    }
}
