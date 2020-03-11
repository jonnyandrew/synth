#include <gtest/gtest.h>
#include <TriangleWaveform.h>
#include "Constants.h"
#include "FloatUtil.h"

using namespace synth;

TEST(TriangleWaveformTest, ItHasCorrectBoundaryPoints) {
    TriangleWaveform triangleWaveform;

    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 0.0), 0);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 0.5), 1);
    EXPECT_NEAR(triangleWaveform.generate(PI * 1.0), 0, testutil::FLOAT_NEAR_ZERO);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 1.5), -1);
    EXPECT_NEAR(triangleWaveform.generate(PI * 2.0), 0, testutil::FLOAT_NEAR_ZERO);
}

TEST(TriangleWaveformTest, ItHasCorrectMidPoints) {
    TriangleWaveform triangleWaveform;

    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 0.125), 0.25);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 0.25), 0.5);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 0.375), 0.75);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 0.625), 0.75);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 0.75), 0.5);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 0.875), 0.25);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 1.125), -0.25);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 1.25), -0.5);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 1.375), -0.75);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 1.625), -0.75);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 1.75), -0.5);
    EXPECT_FLOAT_EQ(triangleWaveform.generate(PI * 1.875), -0.25);
}

TEST(TriangleWaveformTest, ItBehavesOutsideBoundaries) {
    TriangleWaveform triangleWaveform;

    EXPECT_DEATH(triangleWaveform.generate(-PI * 0.125), "");
    EXPECT_DEATH(triangleWaveform.generate(PI * 2.125), "");
}
