#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <array>
#include "../../main/cpp/Oscillator.h"
#include "../../main/cpp/Constants.h"

TEST(RenderTest, WhenWaveIsOffItRendersZeros) {
    Oscillator osc;
    std::array<float_t, 3> buffer;

    osc.render(buffer.data(), buffer.size());

    EXPECT_EQ(buffer[0], 0);
    EXPECT_EQ(buffer[1], 0);
    EXPECT_EQ(buffer[2], 0);
}

TEST(RenderTest, WhenWaveIsOnItRendersAWaveForm) {
    Oscillator osc;
    std::array<float_t, 512> buffer;

    osc.setSampleRate(12345);
    osc.setPitch(60);
    osc.setWaveOn(true);
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

