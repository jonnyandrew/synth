#include <cmath>
#include <gtest/gtest.h>
#include "synth/SineWaveform.h"
#include "synth/Constants.h"
#include "../util/FloatUtil.h"

using namespace synth;

TEST(SineWaveformTest, ItProducesASineWave) {
    SineWaveform sineWaveform;

    for (float i = 0; i <= TWO_PI; i += 0.1) {
        EXPECT_FLOAT_EQ(sineWaveform.generate(PI * i), sin(PI * i));
    }
}

TEST(SineWaveformTest, ItHasCorrectBoundaryPoints) {
    SineWaveform sineWaveform;

    EXPECT_FLOAT_EQ(sineWaveform.generate(PI * 0.0), 0);
    EXPECT_FLOAT_EQ(sineWaveform.generate(PI * 0.5), 1);
    EXPECT_NEAR(sineWaveform.generate(PI * 1.0), 0, testutil::FLOAT_NEAR_ZERO);
    EXPECT_FLOAT_EQ(sineWaveform.generate(PI * 1.5), -1);
    EXPECT_NEAR(sineWaveform.generate(PI * 2.0), 0, testutil::FLOAT_NEAR_ZERO);
}

TEST(SineWaveformTest, ItHasCorrectCurvePoints) {
    SineWaveform sineWaveform;

    EXPECT_FLOAT_EQ(sineWaveform.generate(PI * 0.25), 0.707106781186547);
    EXPECT_FLOAT_EQ(sineWaveform.generate(PI * 0.75), 0.707106781186547);
    EXPECT_FLOAT_EQ(sineWaveform.generate(PI * 1.25), -0.707106781186547);
    EXPECT_FLOAT_EQ(sineWaveform.generate(PI * 1.75), -0.707106781186547);
}
