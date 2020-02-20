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

add_executable(Oscillator_test ./Oscillator_test.cpp ../../main/cpp/Oscillator.cpp)

find_program(ADB adb)

target_link_libraries(
        Oscillator_test
        gtest
)

add_custom_command(TARGET Oscillator_test POST_BUILD COMMAND ${ADB} shell mkdir -p /data/local/tmp/${ANDROID_ABI}
        COMMAND ${ADB} push $<TARGET_FILE:synth-engine> /data/local/tmp/${ANDROID_ABI}/
        COMMAND ${ADB} push $<TARGET_FILE:Oscillator_test> /data/local/tmp/${ANDROID_ABI}/
        COMMAND ${ADB} shell \"export LD_LIBRARY_PATH=/data/local/tmp/${ANDROID_ABI}\; /data/local/tmp/${ANDROID_ABI}/Oscillator_test\")
