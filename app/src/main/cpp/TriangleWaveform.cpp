#include "TriangleWaveform.h"
#include "Constants.h"
#include <cassert>

auto synth::TriangleWaveform::generate(
        const double phase
) -> float {
    double result;
    if (phase <= HALF_PI) {
        result = phase / HALF_PI;
    } else if (phase <= PI) {
        result = (PI - phase) / HALF_PI;
    } else if (phase <= PI + HALF_PI) {
        result = (phase - PI) / -HALF_PI;
    } else {
        result = (phase - PI + HALF_PI) / HALF_PI - 1;
    }

    return static_cast<float>(result);
}
