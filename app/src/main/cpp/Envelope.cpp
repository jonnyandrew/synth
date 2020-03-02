#include <android/log.h>
#include "Envelope.h"
#include "Constants.h"

synth::Envelope::Envelope(
        int sampleRate,
        EnvelopeParameters envelopeParameters
) : params_(envelopeParameters),
    timeIncrement_(MS_PER_S / static_cast<float>(sampleRate)),
    sustainStartTimeMs_(
            calcSustainStartTimeMs_(
                    envelopeParameters.attackTimeMs,
                    envelopeParameters.decayTimeMs
            )
    ),
    attackLevelIncrement_(
            calcAttackLevelIncrement_(
                    envelopeParameters.attackTimeMs,
                    timeIncrement_
            )
    ),
    decayLevelIncrement_(
            calcDecayLevelIncrement_(
                    envelopeParameters.sustainLevel,
                    envelopeParameters.decayTimeMs,
                    timeIncrement_
            )
    ),
    releaseLevelIncrement_(
            calcReleaseLevelIncrement_(
                    envelopeParameters.sustainLevel,
                    envelopeParameters.releaseTimeMs,
                    timeIncrement_
            )
    ) {

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

void synth::Envelope::getSignal(std::vector<float> &buffer) {
    int numFrames = buffer.size();
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
    // Calculate new values
    auto sustainStartTimeMs = calcSustainStartTimeMs_(envelopeParameters.attackTimeMs,
                                                      envelopeParameters.decayTimeMs);
    auto attackLevelIncrement = calcAttackLevelIncrement_(envelopeParameters.attackTimeMs,
                                                          timeIncrement_);
    auto decayLevelIncrement = calcDecayLevelIncrement_(envelopeParameters.sustainLevel,
                                                        envelopeParameters.decayTimeMs,
                                                        timeIncrement_);
    auto releaseLevelIncrement = calcReleaseLevelIncrement_(envelopeParameters.sustainLevel,
                                                            envelopeParameters.releaseTimeMs,
                                                            timeIncrement_);

    // Set values
    params_ = envelopeParameters;
    sustainStartTimeMs_ = sustainStartTimeMs;
    attackLevelIncrement_ = attackLevelIncrement;
    decayLevelIncrement_ = decayLevelIncrement;
    releaseLevelIncrement_ = releaseLevelIncrement;
}

auto synth::Envelope::getEnvelopeParameters() -> synth::EnvelopeParameters {
    return params_;
}

auto synth::Envelope::calcSustainStartTimeMs_(float attackTimeMs, float decayTimeMs) -> float {
    return attackTimeMs + decayTimeMs;
}

auto synth::Envelope::calcAttackLevelIncrement_(float attackTimeMs, float timeIncrement) -> float {
    return MAX_LEVEL / (attackTimeMs / timeIncrement);
}

auto synth::Envelope::calcDecayLevelIncrement_(float sustainLevel, float decayTimeMs,
                                               float timeIncrement) -> float {
    return (sustainLevel - MAX_LEVEL) /
           (decayTimeMs / timeIncrement);
}

auto synth::Envelope::calcReleaseLevelIncrement_(float sustainLevel, float releaseTimeMs,
                                                 float timeIncrement) -> float {
    return (MIN_LEVEL - sustainLevel) /
           (releaseTimeMs / timeIncrement);
}

