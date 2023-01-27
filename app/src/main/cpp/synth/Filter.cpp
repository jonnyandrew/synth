#include "Filter.h"
#include "Constants.h"
#include <cassert>

synth::Filter::Filter(const int sampleRate)
        : sampleRate_{static_cast<float>(sampleRate)} {
    setCutoff(MAX_FREQUENCY);
    setResonance(0.0F);
    vaStateVariableFilter_.setSampleRate(sampleRate_);
}

void synth::Filter::getSignal(std::vector<float> &buffer) {
    if (!isActive_) {
        return;
    }

    vaStateVariableFilter_.processAudioBlock(buffer.data(), buffer.size(), 0);
}

void synth::Filter::setResonance(float resonance) {
    assert(resonance <= 1.0F);
    assert(resonance >= 0.0F);
    resonance_ = resonance;
    vaStateVariableFilter_.setResonance(resonance);
}

void synth::Filter::setCutoff(float cutoff) {
    vaStateVariableFilter_.setCutoffFreq(cutoff);
}

void synth::Filter::setIsActive(bool isActive) {
    isActive_ = isActive;
}

auto synth::Filter::getCutoff() -> float {
    return static_cast<float>(vaStateVariableFilter_.getCutoff());
}

auto synth::Filter::getResonance() -> float {
    return resonance_;
}
