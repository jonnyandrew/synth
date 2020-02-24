#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <array>
#include "Oscillator.h"
#include "Constants.h"

using namespace synth;

TEST(RenderTest, WhenWaveIsOffItRendersZeros) {
    Oscillator osc(123);
    std::array<float, 3> buffer;

    osc.render(buffer.data(), buffer.size());

    EXPECT_EQ(buffer[0], 0);
    EXPECT_EQ(buffer[1], 0);
    EXPECT_EQ(buffer[2], 0);
}

TEST(RenderTest, WhenWaveIsOnItRendersAWaveForm) {
    Oscillator osc(12345);
    std::array<float, 512> buffer;

    osc.setPitch(60);
    osc.render(buffer.data(), buffer.size());

    EXPECT_FLOAT_EQ(buffer[0], 0);
    EXPECT_FLOAT_EQ(buffer[1], 0.132767594256136);
    EXPECT_FLOAT_EQ(buffer[12], 0.999631923345008);
    EXPECT_FLOAT_EQ(buffer[23], 0.078813000205306);
    EXPECT_FLOAT_EQ(buffer[35], -0.998660654911215);
    EXPECT_FLOAT_EQ(buffer[47], -0.024626375061071);
    EXPECT_FLOAT_EQ(buffer[48], 0.108318966530951);
    EXPECT_FLOAT_EQ(buffer[496], -0.074230517071995);
    EXPECT_FLOAT_EQ(buffer[499], -0.45625422286755);
}

