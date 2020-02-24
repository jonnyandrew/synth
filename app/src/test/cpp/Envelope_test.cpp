#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <array>
#include "Envelope.h"

using namespace synth;

TEST(EnvelopeTest, WhenItIsTheFirstEnvelopeItStartsAfterZero) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 1000> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.01);
}

TEST(EnvelopeTest, WhenAttackIsNotTriggeredItOutputsZeros) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 100> buffer;

    envelope.getSignal(buffer.data(), buffer.size());

    for (int i = 0; i < 100; i++) {
        EXPECT_FLOAT_EQ(buffer[i], 0);
    }
}

TEST(EnvelopeTest, ItCompletesTheAttackAtTheCorrectFrame) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 1000> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_GT(buffer[99], buffer[98]);
    EXPECT_GT(buffer[99], buffer[100]);
}

TEST(EnvelopeTest, TheAttackReachesMaximum) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 1000> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[99], 1.0);
}

TEST(EnvelopeTest, ItReachesTheSustainLevelAtTheCorrectFrame) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 1000> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[200], 0.5);
    EXPECT_FLOAT_EQ(buffer[999], 0.5);
}

TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringAttack) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 25> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.25);

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.26);
}

TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringDecay) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 150> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.75);

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.76);
}

TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringSustain) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 1000> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.5);

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.51);
}


TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedAfterRelease) {
    Envelope envelope(1000, 100, 100, 0.5, 100);
    std::array<float, 100> buffer;

    envelope.startAttack();
    envelope.startRelease();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.0);

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.01);
}

TEST(EnvelopeTest, WhenAttackIsZeroItStartsDecayImmediately) {
    Envelope envelope(1000, 0, 100, 0.5, 100);
    std::array<float, 10> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.995);
}

TEST(EnvelopeTest, WhenAttackIsNearZeroItStartsDecayImmediately) {
    Envelope envelope(1000, 0.0001, 100, 0.5, 100);
    std::array<float, 10> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.995);
}

TEST(EnvelopeTest, WhenDecayIsZeroItDecaysStraightToSustain) {
    Envelope envelope(1000, 5, 0, 0.5, 100);
    std::array<float, 10> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[4], 1);
    EXPECT_FLOAT_EQ(buffer[5], 0.5);
}

TEST(EnvelopeTest, WhenDecayIsSmallItDecaysStraightToSustain) {
    Envelope envelope(1000, 5, 0.5, 0.5, 100);
    std::array<float, 10> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[5], 0.5);
}

TEST(EnvelopeTest, WhenDecayAndSustainAreZeroItDecaysStraightToZero) {
    Envelope envelope(1000, 5, 0, 0.0, 100);
    std::array<float, 10> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[4], 1);
    EXPECT_FLOAT_EQ(buffer[5], 0);
}

TEST(EnvelopeTest, WhenAttackDecayAndReleaseAreZero) {
    Envelope envelope(1000, 0, 0, 1.0, 0);
    std::array<float, 1> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 1);

    envelope.startRelease();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0);
}

TEST(EnvelopeTest, WhenAttackAndDecayAreZeroItDropsToSustainImmediately) {
    Envelope envelope(1000, 0, 0, 0.1, 0);
    std::array<float, 1> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.1);
}

TEST(EnvelopeTest, WhenReleaseIsStartedItDropsToZero) {
    Envelope envelope(1000, 0, 0, 1.0, 10);
    std::array<float, 10> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), 1);
    envelope.startRelease();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0.9);
    EXPECT_FLOAT_EQ(buffer[8], 0.1);
    EXPECT_FLOAT_EQ(buffer[9], 0);
}

TEST(EnvelopeTest, WhenReleaseIsFinishedItBecomesExactlyZero) {
    Envelope envelope(1000, 0, 0, 1.0, 10);
    std::array<float, 100> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), 1);
    envelope.startRelease();
    envelope.getSignal(buffer.data(), buffer.size());

    EXPECT_EQ(buffer[11], 0);
}

TEST(EnvelopeTest, WhenReleaseIsStartedBeforeDecayIsFinishedThenItDecaysFirst) {
    Envelope envelope(1000, 10, 10, 0.8, 4);
    std::array<float, 30> buffer;

    envelope.startAttack();
    envelope.getSignal(buffer.data(), 10);
    envelope.startRelease();
    envelope.getSignal(buffer.data(), buffer.size());

    // Expect decay to 0.8
    EXPECT_FLOAT_EQ(buffer[0], 0.98);
    EXPECT_FLOAT_EQ(buffer[9], 0.8);

    // Expect release to 0
    EXPECT_FLOAT_EQ(buffer[10], 0.6);
    EXPECT_FLOAT_EQ(buffer[12], 0.2);
    EXPECT_FLOAT_EQ(buffer[13], 0.0);
}
