#include "SineWaveform.h"
#include "Constants.h"

#include <cassert>
#include <cmath>

auto synth::SineWaveform::generate(
        const double phase
) -> float {
    return static_cast<float>(sin(phase));
}
