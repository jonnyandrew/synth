#ifndef SYNTH_OSCILLATOR_H
#define SYNTH_OSCILLATOR_H

#include <atomic>
#include <stdint.h>
#include <math.h>

class Oscillator {
public:
    void setWaveOn(const bool isWaveOn);

    void setPitch(const int32_t pitch);

    void setSampleRate(const int32_t sampleRate);

    void render(float_t *audioData, const int32_t numFrames);

private:
    std::atomic<bool> isWaveOn_{false};
    double_t phase_ = 0.0;
    double_t phaseIncrement_ = 0.0;
    int32_t sampleRate_;
};

#endif //SYNTH_OSCILLATOR_H
