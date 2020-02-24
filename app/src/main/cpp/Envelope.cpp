#include <android/log.h>
#include "Envelope.h"
#include "Constants.h"

synth::Envelope::Envelope(
        int sampleRate,
        float attackTimeMs,
        float decayTimeMs,
        float sustainLevel,
        float releaseTimeMs) {
    attackTimeMs_ = attackTimeMs;
    sustainLevel_ = sustainLevel;
    timeIncrement_ = (1000.0f / sampleRate);
    sustainStartTimeMs_ = attackTimeMs + decayTimeMs;
    attackLevelIncrement_ = 1.0f / (attackTimeMs / timeIncrement_);
    decayLevelIncrement_ = (sustainLevel - 1.0f) / (decayTimeMs / timeIncrement_);
    releaseLevelIncrement_ = (0.0f - sustainLevel) / (releaseTimeMs / timeIncrement_);
}

void synth::Envelope::startAttack() {
    time_ = 0;
    isTriggering_ = true;
    if(attackTimeMs_ < timeIncrement_) {
        level_ = 1.0f;
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
            if(level_ > sustainLevel_) {
                level_ += decayLevelIncrement_;
            } else {
                level_ += releaseLevelIncrement_;
            }

            if(level_ < 0.0f) level_ = 0.0f;
        } else if (time_ <= attackTimeMs_) {
            level_ += attackLevelIncrement_;
            if(level_ > 1) level_ = 1;
        } else if (time_ <= sustainStartTimeMs_) {
            level_ += decayLevelIncrement_;
            if(level_ < sustainLevel_) level_ = sustainLevel_;
        } else {
            level_ = sustainLevel_;
        }

        // Set the current frame of the buffer
        buffer[i] = static_cast<float>(level_);
    }
}
