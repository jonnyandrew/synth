#include <android/log.h>
#include "Envelope.h"
#include "Constants.h"

synth::Envelope::Envelope(
        int sampleRate,
        EnvelopeParameters envelopeParameters
) : params_(envelopeParameters),
    timeIncrement_(MS_PER_S / sampleRate) {
    onNewParameters();
}

void synth::Envelope::startAttack() {
    time_ = 0;
    isTriggering_ = true;
    if (params_.attackTimeMs < timeIncrement_) {
        level_ = MAX_LEVEL;
    }
}

void synth::Envelope::startRelease() {
    isTriggering_ = false;
}

void synth::Envelope::getSignal(float *buffer, const int numFrames) {
    for (int i = 0; i < numFrames; i++) {
        // Increment the time and level
        time_ += timeIncrement_;
        if (!isTriggering_) {
            // If the release is started before the decay is complete then decay first
            if (level_ > params_.sustainLevel) {
                level_ += decayLevelIncrement_;
            } else {
                level_ += releaseLevelIncrement_;
            }

            if (level_ < MIN_LEVEL) level_ = MIN_LEVEL;
        } else if (time_ <= params_.attackTimeMs) {
            level_ += attackLevelIncrement_;
            if (level_ > MAX_LEVEL) level_ = MAX_LEVEL;
        } else if (time_ <= sustainStartTimeMs_) {
            level_ += decayLevelIncrement_;
            if (level_ < params_.sustainLevel)
                level_ = params_.sustainLevel;
        } else {
            level_ = params_.sustainLevel;
        }

        // Set the current frame of the buffer
        buffer[i] = static_cast<float>(level_);
    }
}

void synth::Envelope::setEnvelopeParameters(synth::EnvelopeParameters envelopeParameters) {
    params_ = envelopeParameters;
    onNewParameters();
}

auto synth::Envelope::getEnvelopeParameters() -> synth::EnvelopeParameters {
    return params_;
}

void synth::Envelope::onNewParameters() {
    sustainStartTimeMs_ = params_.attackTimeMs + params_.decayTimeMs;
    attackLevelIncrement_ = MAX_LEVEL / (params_.attackTimeMs / timeIncrement_);
    decayLevelIncrement_ = (params_.sustainLevel - MAX_LEVEL) /
                           (params_.decayTimeMs / timeIncrement_);
    releaseLevelIncrement_ = (MIN_LEVEL - params_.sustainLevel) /
                             (params_.releaseTimeMs / timeIncrement_);
}

