#include "SineWaveform.h"
#include "Constants.h"

#include <cmath>

synth::SineWaveform::SineWaveform(int label) : Waveform(label) {}

auto synth::SineWaveform::generate(
        const double phase
) -> float {
    return static_cast<float>(sin(phase));
}
