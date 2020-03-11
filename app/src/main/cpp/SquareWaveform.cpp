#include "SquareWaveform.h"
#include "Constants.h"

auto synth::SquareWaveform::generate(
        double phase
) -> float {
    return (phase > PI) ? 1 : -1;
}
