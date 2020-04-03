include(cmake/list_transform.cmake)
set(SYNTH_ENGINE_SOURCES
        jni/AmpEnvelopeJni.cpp
        jni/FilterJni.cpp
        jni/OnLoad.cpp
        jni/OscillatorJni.cpp
        jni/SynthEngineJni.cpp
        jni/model/Synth.cpp
        jni/model/WaveformType.cpp
        synth/AudioEngine.cpp
        synth/AudioStream.cpp
        synth/Envelope.cpp
        synth/EnvelopeControlledAmplifier.cpp
        synth/Filter.cpp
        synth/NoiseWaveform.cpp
        synth/Oscillator.cpp
        synth/PlayException.cpp
        synth/SineWaveform.cpp
        synth/SquareWaveform.cpp
        synth/TriangleWaveform.cpp
        synth/Waveform.cpp
        )
add_library(
        synth-engine
        SHARED
        ${SYNTH_ENGINE_SOURCES}
)
target_link_libraries(
        synth-engine
        libnativehelper
        oboe
        vasvf
        ${log-lib}
)

find_program(CLANG_TIDY clang-tidy)

set(CLANG_TIDY_CHECKS
        "*,\
android-*,\
-fuchsia-*,\
-cppcoreguidelines-pro-bounds-array-to-pointer-decay,\
-cppcoreguidelines-pro-bounds-pointer-arithmetic,\
-cppcoreguidelines-pro-type-vararg,\
-hicpp-no-array-decay,\
-hicpp-vararg,\
-clang-analyzer-core.uninitialized.UndefReturn,\
-clang-diagnostic-unused-command-line-argument")

set(LINT_RUNNER ${CMAKE_BINARY_DIR}/cpp_lint_runner.sh)

list_transform_prepend(SYNTH_ENGINE_SOURCES "${CMAKE_CURRENT_SOURCE_DIR}/")
add_custom_command(
        TARGET synth-engine POST_BUILD
        VERBATIM COMMAND > ${LINT_RUNNER}
        VERBATIM COMMAND echo ${CLANG_TIDY} \"\$@\" -p=${CMAKE_BINARY_DIR} -warnings-as-errors=${CLANG_TIDY_CHECKS} -checks=${CLANG_TIDY_CHECKS} ${SYNTH_ENGINE_SOURCES} >> ${LINT_RUNNER}
)

