set(GOOGLETEST_ROOT ${ANDROID_NDK}/sources/third_party/googletest)

add_library(
        gtest
        STATIC
        ${GOOGLETEST_ROOT}/src/gtest_main.cc
        ${GOOGLETEST_ROOT}/src/gtest-all.cc
)

target_include_directories(
        gtest
        PRIVATE
        ${GOOGLETEST_ROOT}
)

target_include_directories(
        gtest
        PUBLIC
        ${GOOGLETEST_ROOT}/include
)

target_include_directories(
        gtest
        PUBLIC
        ../../main/cpp
)

add_executable(unit-tests
        synth/AudioEngine_test.cpp
        synth/Envelope_test.cpp
        synth/EnvelopeControlledAmplifier_test.cpp
        synth/Oscillator_test.cpp
        synth/Pitch_test.cpp
        synth/SineWaveform_test.cpp
        synth/SquareWaveform_test.cpp
        synth/TriangleWaveform_test.cpp
        )

find_program(ADB adb)

target_link_libraries(
        unit-tests
        gtest
        synth-engine
)

set(TEST_RUNNER ${CPP_SCRIPT_DIR}/${CMAKE_ANDROID_ARCH_ABI}/cpp_test_runner.sh)

add_custom_command(
        TARGET unit-tests POST_BUILD
        COMMAND > ${TEST_RUNNER}
        COMMAND echo ${ADB} shell mkdir -p /data/local/tmp/${ANDROID_ABI} >> ${TEST_RUNNER}
        COMMAND echo ${ADB} push $<TARGET_FILE:synth-engine> /data/local/tmp/${ANDROID_ABI}/ >> ${TEST_RUNNER}
        COMMAND echo ${ADB} push $<TARGET_FILE:unit-tests> /data/local/tmp/${ANDROID_ABI}/ >> ${TEST_RUNNER}
        COMMAND echo ${ADB} shell LD_LIBRARY_PATH=/data/local/tmp/${ANDROID_ABI} /data/local/tmp/${ANDROID_ABI}/unit-tests >> ${TEST_RUNNER}
)

