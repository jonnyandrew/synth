#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <vector>
#include <Pitch.h>
#include <SineWaveform.h>
#include "Oscillator.h"
#include "Constants.h"

using namespace synth;

TEST(OscillatorTest, WhenConfigurationIsDefaultItRendersSomething) {
    auto sineWaveform = std::make_unique<SineWaveform>();

    Oscillator osc(123, std::move(sineWaveform));
    std::vector<float> buffer(3);

    osc.render(buffer, buffer.size());

    EXPECT_NE(buffer[1], 0);
    EXPECT_NE(buffer[2], 0);
}

TEST(OscillatorTest, WhenWaveIsOnItRendersAWaveForm) {
    auto sineWaveform = std::make_unique<SineWaveform>();

    Oscillator osc(12345, std::move(sineWaveform));
    std::vector<float> buffer(512);

    osc.setPitch(60);
    osc.render(buffer, buffer.size());

    // Expect middle C sine wave
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

TEST(OscillatorTest, WhenPitchOffsetIsSetItChangesThePitch) {
    auto sineWaveform = std::make_unique<SineWaveform>();

    Oscillator osc(12345, std::move(sineWaveform));
    osc.setPitch(21);
    osc.setPitchOffset(22);
    const auto result = osc.getFrequency();

    EXPECT_EQ(result, PITCH_FREQUENCIES[43]);
}

TEST(OscillatorTest, WhenPitchOffsetIsTooLargeItClampsTheOffset) {
    auto sineWaveform = std::make_unique<SineWaveform>();

    Oscillator osc(12345, std::move(sineWaveform));
    osc.setPitch(60);
    osc.setPitchOffset(100);
    const auto result = osc.getFrequency();

    EXPECT_EQ(result, PITCH_FREQUENCIES[127]);
}

TEST(OscillatorTest, WhenPitchOffsetIsTooSmallItClampsTheOffset) {
    auto sineWaveform = std::make_unique<SineWaveform>();

    Oscillator osc(12345, std::move(sineWaveform));
    osc.setPitch(60);
    osc.setPitchOffset(-100);
    const auto result = osc.getFrequency();

    EXPECT_EQ(result, PITCH_FREQUENCIES[0]);
}
