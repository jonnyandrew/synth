#include "Oscillator.h"
#include "Constants.h"
#include "Pitch.h"
#include <math.h>

void Oscillator::setSampleRate(const int32_t sampleRate) {
    sampleRate_ = sampleRate;
}

void Oscillator::setWaveOn(const bool isWaveOn) {
    isWaveOn_.store(isWaveOn);
}

void Oscillator::render(float_t *audioData, const int32_t numFrames) {
    const double_t amplitude = 1.0;

    if (!isWaveOn_.load()) phase_ = 0;

    for (int i = 0; i < numFrames; i++) {

        if (isWaveOn_.load()) {

            // Calculates the next sample value for the sine wave.
            audioData[i] = static_cast<float_t>(sin(phase_) * amplitude);

            // Increments the phase, handling wrap around.
            phase_ += phaseIncrement_;
            if (phase_ > TWO_PI) phase_ -= TWO_PI;

        } else {
            // Outputs silence by setting sample value to zero.
            audioData[i] = 0;
        }
    }
}

void Oscillator::setPitch(const int32_t pitch) {
    const double_t frequency = PITCH_FREQUENCIES[pitch];
    phaseIncrement_ = (TWO_PI * frequency) / static_cast<double_t>(sampleRate_);
}
