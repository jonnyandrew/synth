#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <array>
#include <math.h>
#include "synth/EnvelopeControlledAmplifier.h"

using namespace synth;

TEST(EnvelopeControlledAmplifierTest, AmplifierProducesPower4Curve) {
    EnvelopeParameters params = {100, 100, 0.5, 100};
    Envelope envelope(1000, params);
    std::vector<float> audioBuffer(10, 1.0F);
    EnvelopeControlledAmplifier subject(envelope);

    subject.startAttack();
    subject.getSignal(audioBuffer);

    for(int i = 0; i < 10; i++) {
        EXPECT_FLOAT_EQ(audioBuffer[i], pow((i + 1.0) / 100.0, 4));
    }
}

TEST(EnvelopeTest, AmplifierDoesNotAmplifySilentAudio) {
    EnvelopeParameters params = {100, 100, 0.5, 100};
    Envelope envelope(1000, params);
    // Silent audio
    std::vector<float> audioBuffer(10, 0);
    EnvelopeControlledAmplifier subject(envelope);

    subject.startAttack();
    subject.getSignal(audioBuffer);

    for(int i = 0; i < 10; i++) {
        EXPECT_FLOAT_EQ(audioBuffer[i], 0);
    }
}

TEST(EnvelopeTest, AmplifierDoesNotAmplifyIfStartAttackNotCalled) {
    EnvelopeParameters params = {100, 100, 0.5, 100};
    Envelope envelope(1000, params);
    std::vector<float> audioBuffer(10, 1.0F);
    EnvelopeControlledAmplifier subject(envelope);

    subject.getSignal(audioBuffer);

    for(int i = 0; i < 10; i++) {
        EXPECT_FLOAT_EQ(audioBuffer[i], 0);
    }
}

TEST(EnvelopeTest, AmplifierReleasesTheEnvelopeIfStartReleaseCalled) {
    EnvelopeParameters params = {10, 0, 0.5, 0};
    Envelope envelope(1000, params);
    std::vector<float> audioBuffer(1, 1.0F);
    EnvelopeControlledAmplifier subject(envelope);

    subject.startAttack();
    subject.getSignal(audioBuffer);

    // Expect that the audio was started
    EXPECT_GT(audioBuffer[0], 0);

    subject.startRelease();
    subject.getSignal(audioBuffer);

    // Expect that the attack was stopped and audio drops to silent
    // According to this envelope
    EXPECT_FLOAT_EQ(audioBuffer[0], 0);
}
