#include "Oscillator.h"
#include "Constants.h"
#include "Pitch.h"
#include "SineWaveform.h"
#include "SquareWaveform.h"
#include "TriangleWaveform.h"
#include <cassert>
#include <cmath>

synth::Oscillator::Oscillator(
        const int sampleRate,
        std::unique_ptr<Waveform> waveform
) :
        sampleRate_{sampleRate},
        pitch_{0},
        pitchOffset_{0},
        frequency_{calcFrequency(pitch_, pitchOffset_)},
        waveform_{std::move(waveform)} {
    assert(sampleRate > 0);
}

void synth::Oscillator::render(std::vector<float> &audioData, const int numFrames) {
    const auto phaseIncrement =
            (TWO_PI * frequency_) / static_cast<double>(sampleRate_);
    for (int i = 0; i < numFrames; i++) {
        audioData[i] = waveform_->generate(phase_);

        phase_ += phaseIncrement;
        if (phase_ > TWO_PI) { phase_ -= TWO_PI; }
    }
}

void synth::Oscillator::setPitch(const int pitch) {
    pitch_ = pitch;
    frequency_ = calcFrequency(pitch, pitchOffset_);
}

void synth::Oscillator::setPitchOffset(const int pitchOffset) {
    pitchOffset_ = pitchOffset;
    frequency_ = calcFrequency(pitch_, pitchOffset);
}

auto synth::Oscillator::getPitchOffset() -> int {
    return pitchOffset_;
}

auto synth::Oscillator::calcFrequency(
        int pitch, int pitchOffset
) -> double {
    const auto clampedPitch = std::max(0, std::min(127, pitch + pitchOffset));
    return PITCH_FREQUENCIES[clampedPitch];
}

auto synth::Oscillator::getFrequency() -> double {
    return frequency_;
}

void synth::Oscillator::setWaveform(std::unique_ptr<synth::Waveform> waveform) {
    waveform_ = std::move(waveform);
}

auto synth::Oscillator::getWaveform() -> synth::Waveform & {
    return *waveform_;
}

