#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <array>
#include <math.h>
#include "EnvelopeControlledAmplifier.h"

using namespace synth;

TEST(EnvelopeControlledAmplifierTest, AmplifierProducesPower4Curve) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 10> audioBuffer;
    audioBuffer.fill(1.0);
    EnvelopeControlledAmplifier subject(envelope);

    subject.startAttack();
    subject.getSignal(audioBuffer.data(), audioBuffer.size());

    for(int i = 0; i < 10; i++) {
        EXPECT_FLOAT_EQ(audioBuffer[i], pow((i + 1.0) / 100.0, 4));
    }
}

TEST(EnvelopeTest, AmplifierDoesNotAmplifySilentAudio) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 10> audioBuffer;
    // Silent audio
    audioBuffer.fill(0);
    EnvelopeControlledAmplifier subject(envelope);

    subject.startAttack();
    subject.getSignal(audioBuffer.data(), audioBuffer.size());

    for(int i = 0; i < 10; i++) {
        EXPECT_FLOAT_EQ(audioBuffer[i], 0);
    }
}

TEST(EnvelopeTest, AmplifierDoesNotAmplifyIfStartAttackNotCalled) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 10> audioBuffer;
    audioBuffer.fill(1.0);
    EnvelopeControlledAmplifier subject(envelope);

    subject.getSignal(audioBuffer.data(), audioBuffer.size());

    for(int i = 0; i < 10; i++) {
        EXPECT_FLOAT_EQ(audioBuffer[i], 0);
    }
}

TEST(EnvelopeTest, AmplifierReleasesTheEnvelopeIfStartReleaseCalled) {
    Envelope envelope(1000, 10, 0, 0.5, 0);
    std::array<float, 100> audioBuffer;
    audioBuffer.fill(1.0);
    EnvelopeControlledAmplifier subject(envelope);

    subject.startAttack();
    subject.getSignal(audioBuffer.data(), 1);

    // Expect that the audio was started
    EXPECT_GT(audioBuffer[0], 0);

    subject.startRelease();
    subject.getSignal(audioBuffer.data(), 9);

    // Expect that the attack was stopped and audio drops to silent
    // According to this envelope
    EXPECT_FLOAT_EQ(audioBuffer[0], 0);
}
