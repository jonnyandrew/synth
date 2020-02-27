#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <array>
#include "Envelope.h"

using namespace synth;

namespace {

    const int ONE_FRAME_PER_SEC = 1000;

    TEST(EnvelopeTest, WhenItIsTheFirstEnvelopeItStartsAfterZero) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 1000> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 0.01);
    }

    TEST(EnvelopeTest, WhenAttackIsNotTriggeredItOutputsZeros) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 100> buffer;

        envelope.getSignal(buffer.data(), buffer.size());

        for (int i = 0; i < 100; i++) {
            EXPECT_FLOAT_EQ(buffer[i], 0);
        }
    }

    TEST(EnvelopeTest, ItCompletesTheAttackAtTheCorrectFrame) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 1000> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_GT(buffer[99], buffer[98]);
        EXPECT_GT(buffer[99], buffer[100]);
    }

    TEST(EnvelopeTest, TheAttackReachesMaximum) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 1000> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[99], 1.0);
    }

    TEST(EnvelopeTest, ItReachesTheSustainLevelAtTheCorrectFrame) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 1000> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[200], 0.5);
        EXPECT_FLOAT_EQ(buffer[999], 0.5);
    }

    TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringAttack) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 25> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.25);

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 0.26);
    }

    TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringDecay) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 150> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.75);

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 0.76);
    }

    TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringSustain) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 1000> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.5);

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 0.51);
    }


    TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedAfterRelease) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
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
        EnvelopeParameters params = {0, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 10> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 0.995);
    }

    TEST(EnvelopeTest, WhenAttackIsNearZeroItStartsDecayImmediately) {
        EnvelopeParameters params = {0.0001, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 10> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 0.995);
    }

    TEST(EnvelopeTest, WhenDecayIsZeroItDecaysStraightToSustain) {
        EnvelopeParameters params = {5, 0, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 10> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[4], 1);
        EXPECT_FLOAT_EQ(buffer[5], 0.5);
    }

    TEST(EnvelopeTest, WhenDecayIsSmallItDecaysStraightToSustain) {
        EnvelopeParameters params = {5, 0.5, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 10> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[5], 0.5);
    }

    TEST(EnvelopeTest, WhenDecayAndSustainAreZeroItDecaysStraightToZero) {
        EnvelopeParameters params = {5, 0, 0.0, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 10> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[4], 1);
        EXPECT_FLOAT_EQ(buffer[5], 0);
    }

    TEST(EnvelopeTest, WhenAttackDecayAndReleaseAreZero) {
        EnvelopeParameters params = {0, 0, 1.0, 0};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 1> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 1);

        envelope.startRelease();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 0);
    }

    TEST(EnvelopeTest, WhenAttackAndDecayAreZeroItDropsToSustainImmediately) {
        EnvelopeParameters params = {0, 0, 0.1, 0};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 1> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 0.1);
    }

    TEST(EnvelopeTest, WhenReleaseIsStartedItDropsToZero) {
        EnvelopeParameters params = {0, 0, 1.0, 10};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
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
        EnvelopeParameters params = {0, 0, 1.0, 10};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::array<float, 100> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), 1);
        envelope.startRelease();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_EQ(buffer[11], 0);
    }

    TEST(EnvelopeTest, WhenReleaseIsStartedBeforeDecayIsFinishedThenItDecaysFirst) {
        EnvelopeParameters params = {10, 10, 0.8, 4};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
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

    TEST(EnvelopeTest, WhenEnvelopeParametersAreChangedAfterInitItChangesTheOutput) {
        EnvelopeParameters initialParams = {10, 10, 0.8, 4};
        Envelope envelope(ONE_FRAME_PER_SEC, initialParams);
        std::array<float, 2> buffer;

        envelope.setEnvelopeParameters({1, 1, 0.5, 1});

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());

        EXPECT_FLOAT_EQ(buffer[0], 1.0);
        EXPECT_FLOAT_EQ(buffer[1], 0.5);
    }

    TEST(EnvelopeTest, WhenEnvelopeParametersAreChangedDuringAttackItChangesTheOutput) {
        EnvelopeParameters initialParams = {10, 10, 0.8, 4};
        Envelope envelope(ONE_FRAME_PER_SEC, initialParams);
        std::array<float, 2> buffer;

        envelope.startAttack();
        envelope.getSignal(buffer.data(), buffer.size());
        envelope.setEnvelopeParameters({1, 1, 0.5, 1});
        envelope.getSignal(buffer.data(), buffer.size());


        // now at frame indices 2 and 3, the new envelope is already at sustain
        EXPECT_FLOAT_EQ(buffer[0], 0.5);
        EXPECT_FLOAT_EQ(buffer[1], 0.5);
    }
}
