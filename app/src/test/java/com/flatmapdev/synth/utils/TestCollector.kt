package com.flatmapdev.synth.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.assertj.core.api.Assertions.assertThat

class TestCollector<T>(
    scope: CoroutineScope,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()

    private val job = scope.launch { flow.collect { values.add(it) } }

    fun assertValues(vararg _values: T): TestCollector<T> {
        assertThat(values).contains(*_values)
        return this
    }

    fun finish() {
        job.cancel()
    }
}

fun <T> Flow<T>.test(scope: CoroutineScope): TestCollector<T> {
    return TestCollector(scope, this)
}
