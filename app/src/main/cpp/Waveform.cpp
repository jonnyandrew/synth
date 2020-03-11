
#include "Waveform.h"

synth::Waveform::Waveform(int label) : label{label} {}

auto synth::Waveform::getLabel() -> int {
    return label;
}

