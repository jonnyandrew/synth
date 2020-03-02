#include <math.h>
#include "EnvelopeControlledAmplifier.h"

synth::EnvelopeControlledAmplifier::EnvelopeControlledAmplifier(
        Envelope &envelope
) : envelope_(&envelope) {}

void synth::EnvelopeControlledAmplifier::getSignal(
        float *audioBuffer,
        const int numFrames
) {
    float envelopeBuffer[numFrames];

    envelope_->getSignal(envelopeBuffer, numFrames);

    for (int i = 0; i < numFrames; i++) {
        // The human ear perceives an exponential scale of volume
        // as if it is linear. So we need to convert the envelope
        // which is on a linear scale, to an exponential curve.
        // Use x^4 as an approximation of an exponential curve
        // which also conveniently passes through x,y = 0,0 and 1,1
        const auto exponent = 4.0F;
        audioBuffer[i] *= pow(envelopeBuffer[i], exponent);
    }
}

void synth::EnvelopeControlledAmplifier::startAttack() {
    envelope_->startAttack();
}

void synth::EnvelopeControlledAmplifier::startRelease() {
    envelope_->startRelease();
}

