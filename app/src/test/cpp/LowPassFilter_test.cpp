#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <vector>
#include <Pitch.h>
#include <SineWaveform.h>
#include <LowPassFilter.h>

using namespace synth;

TEST(LowPassFilterTest, ItAppliesTheLowPassFilter) {
    LowPassFilter lowPassFilter(48000);
    std::vector<float> buffer(10, 0);
    buffer[0] = 1;

    lowPassFilter.setCutoff(12345);
    lowPassFilter.setResonance(3);
    lowPassFilter.getSignal(buffer);

    EXPECT_FLOAT_EQ(buffer[0], -3.1135492e-05);
    EXPECT_FLOAT_EQ(buffer[1], -0.00023804353);
    EXPECT_FLOAT_EQ(buffer[2], -0.00088941882);
    EXPECT_FLOAT_EQ(buffer[3], -0.002224941);
    EXPECT_FLOAT_EQ(buffer[4], -0.0043026763);
    EXPECT_FLOAT_EQ(buffer[5], -0.0069953115);
    EXPECT_FLOAT_EQ(buffer[6], -0.010070338);
    EXPECT_FLOAT_EQ(buffer[7], -0.013263225);
    EXPECT_FLOAT_EQ(buffer[8], -0.016322533);
    EXPECT_FLOAT_EQ(buffer[9], -0.019032311);
}
