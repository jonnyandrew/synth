
#include <gtest/gtest.h>
#include <math.h>
#include "Pitch.h"

using namespace synth;

TEST(PitchTest, Pitch0IsCMINUS1) {
    const double CMINUS1_FREQUENCY = 8.18;
    EXPECT_DOUBLE_EQ(PITCH_FREQUENCIES[0], CMINUS1_FREQUENCY);
}

TEST(PitchTest, Pitch60IsMiddleC) {
    const double MIDDLE_C_FREQUENCY = 261.63;
    EXPECT_DOUBLE_EQ(PITCH_FREQUENCIES[60], MIDDLE_C_FREQUENCY);
}

TEST(PitchTest, Pitch69IsA440) {
    const double A440_FREQUENCY = 440.00;
    EXPECT_DOUBLE_EQ(PITCH_FREQUENCIES[69], A440_FREQUENCY);
}

TEST(PitchTest, Pitch127IsG9) {
    const double G9_FREQUENCY = 12543.85;
    EXPECT_DOUBLE_EQ(PITCH_FREQUENCIES[127], G9_FREQUENCY);
}
