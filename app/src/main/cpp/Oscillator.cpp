#include "Oscillator.h"
#include "Constants.h"
#include "Pitch.h"
#include <cmath>

synth::Oscillator::Oscillator(const int sampleRate) {
    assert(sampleRate > 0);
    sampleRate_ = sampleRate;
}

void synth::Oscillator::render(std::vector<float> &audioData, const int numFrames) {
    for (int i = 0; i < numFrames; i++) {
        // Calculates the next sample value for the sine wave.
        audioData[i] = static_cast<float>(sin(phase_));

        // Increments the phase, handling wrap around.
        phase_ += phaseIncrement_;
        if (phase_ > TWO_PI) { phase_ -= TWO_PI; }
    }
}

void synth::Oscillator::setPitch(const int pitch) {
    const double frequency = PITCH_FREQUENCIES[pitch];
    phaseIncrement_ = (TWO_PI * frequency) / static_cast<double>(sampleRate_);
}

