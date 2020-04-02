cmake_minimum_required(VERSION 3.10.2)

set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

add_subdirectory(libs/cpp)

add_subdirectory(src/main/cpp)

add_subdirectory(src/test/cpp)
