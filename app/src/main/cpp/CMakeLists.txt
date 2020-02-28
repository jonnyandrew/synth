set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

file(GLOB SYNTH_ENGINE_SOURCES ./*.cpp)

add_library(
        synth-engine
        SHARED
        ${SYNTH_ENGINE_SOURCES}
        )
target_link_libraries(
        synth-engine
        oboe
        ${log-lib}
        )


