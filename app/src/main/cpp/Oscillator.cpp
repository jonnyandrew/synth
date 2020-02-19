#include "Oscillator.h"
#include "Constants.h"
#include "Pitch.h"
#include <math.h>

void Oscillator::setSampleRate(int32_t sampleRate) {
    sampleRate_ = sampleRate;
}

void Oscillator::setWaveOn(bool isWaveOn) {
    isWaveOn_.store(isWaveOn);
}

void Oscillator::render(float *audioData, int32_t numFrames) {
    double_t amplitude = 1.0;

    if (!isWaveOn_.load()) phase_ = 0;

    for (int i = 0; i < numFrames; i++) {

        if (isWaveOn_.load()) {

            // Calculates the next sample value for the sine wave.
            audioData[i] = (float) (sin(phase_) * amplitude);

            // Increments the phase, handling wrap around.
            phase_ += phaseIncrement_;
            if (phase_ > TWO_PI) phase_ -= TWO_PI;

        } else {
            // Outputs silence by setting sample value to zero.
            audioData[i] = 0;
        }
    }
}

void Oscillator::setPitch(int32_t pitch) {
    double_t frequency = PITCH_FREQUENCIES[pitch];
    phaseIncrement_ = (TWO_PI * frequency) / static_cast<double_t>(sampleRate_);
}
