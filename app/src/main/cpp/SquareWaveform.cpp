#include "SquareWaveform.h"
#include "Constants.h"

synth::SquareWaveform::SquareWaveform(int label) : Waveform(label) {}

auto synth::SquareWaveform::generate(
        double phase
) -> float {
    return (phase > PI) ? 1 : -1;
}
