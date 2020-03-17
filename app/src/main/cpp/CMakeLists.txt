
set(SYNTH_ENGINE_SOURCES
        AudioEngine.cpp
        AudioStream.cpp
        Envelope.cpp
        EnvelopeControlledAmplifier.cpp
        Filter.cpp
        native-lib.cpp
        NoiseWaveform.cpp
        Oscillator.cpp
        PlayException.cpp
        SineWaveform.cpp
        SquareWaveform.cpp
        TriangleWaveform.cpp
        Waveform.cpp
        )
add_library(
        synth-engine
        SHARED
        ${SYNTH_ENGINE_SOURCES}
)
target_link_libraries(
        synth-engine
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
-clang-diagnostic-unused-command-line-argument")

set(LINT_RUNNER ${CMAKE_BINARY_DIR}/cpp_lint_runner.sh)

add_custom_command(
        TARGET synth-engine POST_BUILD
        VERBATIM COMMAND > ${LINT_RUNNER}
        VERBATIM COMMAND echo ${CLANG_TIDY} \"\$@\" -p=${CMAKE_BINARY_DIR} -warnings-as-errors=${CLANG_TIDY_CHECKS} -checks=${CLANG_TIDY_CHECKS} ${SYNTH_ENGINE_SOURCES} >> ${LINT_RUNNER}
)

