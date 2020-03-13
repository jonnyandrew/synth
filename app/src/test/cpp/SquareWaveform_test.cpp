#include <gtest/gtest.h>
#include <Pitch.h>
#include <SquareWaveform.h>
#include "Constants.h"

using namespace synth;

TEST(SquareWaveformTest, ItHasCorrectBoundaryPoints) {
    SquareWaveform squareWaveform;

    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 0.0), -1);
    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 1.0), -1);
    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 2.0), 1);
}

TEST(SquareWaveformTest, ItHasCorrectMidPoints) {
    SquareWaveform squareWaveform;

    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 0.5), -1);
    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 1.5), 1);
}

TEST(SquareWaveformTest, ItIsCorrectCloseToTheBoundaries) {
    SquareWaveform squareWaveform;
    constexpr float smallAmount = 1.0e-15;

    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 0.0 + smallAmount), -1);
    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 1.0 - smallAmount), -1);
    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 1.0 + smallAmount), 1);
    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 2.0 - smallAmount), 1);
}

TEST(SquareWaveformTest, ItCanAcceptInputsOutsideBoundaries) {
    SquareWaveform squareWaveform;

    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * -0.1), -1);
    EXPECT_FLOAT_EQ(squareWaveform.generate(PI * 2.1), 1);
}
