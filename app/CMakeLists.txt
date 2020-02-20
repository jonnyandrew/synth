cmake_minimum_required(VERSION 3.4.1)

add_subdirectory(src/main/cpp)

if (DEFINED RUN_UNIT_TESTS AND RUN_UNIT_TESTS STREQUAL "TRUE")
    add_subdirectory(src/test/cpp)
endif ()
