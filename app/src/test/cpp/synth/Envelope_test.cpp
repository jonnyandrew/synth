#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <array>
#include "synth/Envelope.h"

using namespace synth;

namespace {

    const int ONE_FRAME_PER_SEC = 1000;

    TEST(EnvelopeTest, WhenItIsTheFirstEnvelopeItStartsAfterZero) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(1000);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0.01);
    }

    TEST(EnvelopeTest, WhenAttackIsNotTriggeredItOutputsZeros) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(100);

        envelope.getSignal(buffer);

        for (int i = 0; i < 100; i++) {
            EXPECT_FLOAT_EQ(buffer[i], 0);
        }
    }

    TEST(EnvelopeTest, ItCompletesTheAttackAtTheCorrectFrame) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(1000);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_GT(buffer[99], buffer[98]);
        EXPECT_GT(buffer[99], buffer[100]);
    }

    TEST(EnvelopeTest, TheAttackReachesMaximum) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(1000);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[99], 1.0);
    }

    TEST(EnvelopeTest, ItReachesTheSustainLevelAtTheCorrectFrame) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(1000);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[200], 0.5);
        EXPECT_FLOAT_EQ(buffer[999], 0.5);
    }

    TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringAttack) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(25);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.25);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0.26);
    }

    TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringDecay) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(150);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.75);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0.76);
    }

    TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedDuringSustain) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(1000);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.5);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0.51);
    }


    TEST(EnvelopeTest, ItContinuesFromWhereThePreviousEnvelopeStoppedAfterRelease) {
        EnvelopeParameters params = {100, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(100);

        envelope.startAttack();
        envelope.startRelease();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[buffer.size() - 1], 0.0);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0.01);
    }

    TEST(EnvelopeTest, WhenAttackIsZeroItStartsDecayImmediately) {
        EnvelopeParameters params = {0, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(10);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0.995);
    }

    TEST(EnvelopeTest, WhenAttackIsNearZeroItStartsDecayImmediately) {
        EnvelopeParameters params = {0.0001, 100, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(10);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0.995);
    }

    TEST(EnvelopeTest, WhenDecayIsZeroItDecaysStraightToSustain) {
        EnvelopeParameters params = {5, 0, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(10);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[4], 1);
        EXPECT_FLOAT_EQ(buffer[5], 0.5);
    }

    TEST(EnvelopeTest, WhenDecayIsSmallItDecaysStraightToSustain) {
        EnvelopeParameters params = {5, 0.5, 0.5, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(10);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[5], 0.5);
    }

    TEST(EnvelopeTest, WhenDecayAndSustainAreZeroItDecaysStraightToZero) {
        EnvelopeParameters params = {5, 0, 0.0, 100};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(10);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[4], 1);
        EXPECT_FLOAT_EQ(buffer[5], 0);
    }

    TEST(EnvelopeTest, WhenAttackDecayAndReleaseAreZero) {
        EnvelopeParameters params = {0, 0, 1.0, 0};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(1);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 1);

        envelope.startRelease();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0);
    }

    TEST(EnvelopeTest, WhenAttackAndDecayAreZeroItDropsToSustainImmediately) {
        EnvelopeParameters params = {0, 0, 0.1, 0};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer(1);

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 0.1);
    }

    TEST(EnvelopeTest, WhenReleaseIsStartedItDropsToZero) {
        EnvelopeParameters params = {0, 0, 1.0, 10};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer1(1);
        std::vector<float> buffer2(10);

        envelope.startAttack();
        envelope.getSignal(buffer1);
        envelope.startRelease();
        envelope.getSignal(buffer2);

        EXPECT_FLOAT_EQ(buffer2[0], 0.9);
        EXPECT_FLOAT_EQ(buffer2[8], 0.1);
        EXPECT_FLOAT_EQ(buffer2[9], 0);
    }

    TEST(EnvelopeTest, WhenReleaseIsFinishedItBecomesExactlyZero) {
        EnvelopeParameters params = {0, 0, 1.0, 10};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer1(1);
        std::vector<float> buffer2(100);

        envelope.startAttack();
        envelope.getSignal(buffer1);
        envelope.startRelease();
        envelope.getSignal(buffer2);

        EXPECT_EQ(buffer2[11], 0);
    }

    TEST(EnvelopeTest, WhenReleaseIsStartedBeforeDecayIsFinishedThenItDecaysFirst) {
        EnvelopeParameters params = {10, 10, 0.8, 4};
        Envelope envelope(ONE_FRAME_PER_SEC, params);
        std::vector<float> buffer1(10);
        std::vector<float> buffer2(30);

        envelope.startAttack();
        envelope.getSignal(buffer1);
        envelope.startRelease();
        envelope.getSignal(buffer2);

        // Expect decay to 0.8
        EXPECT_FLOAT_EQ(buffer2[0], 0.98);
        EXPECT_FLOAT_EQ(buffer2[9], 0.8);

        // Expect release to 0
        EXPECT_FLOAT_EQ(buffer2[10], 0.6);
        EXPECT_FLOAT_EQ(buffer2[12], 0.2);
        EXPECT_FLOAT_EQ(buffer2[13], 0.0);
    }

    TEST(EnvelopeTest, WhenEnvelopeParametersAreChangedAfterInitItChangesTheOutput) {
        EnvelopeParameters initialParams = {10, 10, 0.8, 4};
        Envelope envelope(ONE_FRAME_PER_SEC, initialParams);
        std::vector<float> buffer(2);

        envelope.setEnvelopeParameters({1, 1, 0.5, 1});

        envelope.startAttack();
        envelope.getSignal(buffer);

        EXPECT_FLOAT_EQ(buffer[0], 1.0);
        EXPECT_FLOAT_EQ(buffer[1], 0.5);
    }

    TEST(EnvelopeTest, WhenEnvelopeParametersAreChangedDuringAttackItChangesTheOutput) {
        EnvelopeParameters initialParams = {10, 10, 0.8, 4};
        Envelope envelope(ONE_FRAME_PER_SEC, initialParams);
        std::vector<float> buffer(2);

        envelope.startAttack();
        envelope.getSignal(buffer);
        envelope.setEnvelopeParameters({1, 1, 0.5, 1});
        envelope.getSignal(buffer);

        // now at frame indices 2 and 3, the new envelope is already at sustain
        EXPECT_FLOAT_EQ(buffer[0], 0.5);
        EXPECT_FLOAT_EQ(buffer[1], 0.5);
    }
}
