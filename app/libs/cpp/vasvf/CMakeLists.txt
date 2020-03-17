project(vasvf)

add_library(
        vasvf
        src/VAStateVariableFilter.cpp
        src/DSPUtilities.cpp
)

target_include_directories(
        vasvf
        PUBLIC src
)
