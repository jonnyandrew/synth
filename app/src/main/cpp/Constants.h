#ifndef SYNTH_CONSTANTS_H
#define SYNTH_CONSTANTS_H

namespace synth {
    constexpr double PI = 3.14159265358979323846;
    constexpr auto HALF_PI = PI / 2;
    constexpr auto TWO_PI = PI * 2;
    const char *const LOGGER_TAG = "CppSynth";
    constexpr auto MAX_LEVEL = 1.0F;
    constexpr auto MIN_LEVEL = 0.0F;
    const auto MS_PER_S = 1000.0F;
}

#endif //SYNTH_CONSTANTS_H
