#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <vector>
#include <Pitch.h>
#include <SineWaveform.h>
#include "Oscillator.h"
#include "Constants.h"
#include "../../main/cpp/AudioEngine.h"

using namespace synth;

TEST(AudioEngineTest, WhenKeyIsPressedItSetsTheOscillatorPitches) {
    constexpr auto sampleRate = 1000;
    auto sineWaveform1 = std::make_unique<SineWaveform>();
    Oscillator oscillator1{sampleRate, std::move(sineWaveform1)};
    auto sineWaveform2 = std::make_unique<SineWaveform>();
    Oscillator oscillator2{sampleRate, std::move(sineWaveform2)};
    Envelope envelope{sampleRate, {100, 100, 0.4, 100}};
    EnvelopeControlledAmplifier envelopeControlledAmplifier{envelope};
    AudioEngine audioEngine(oscillator1, oscillator2, envelopeControlledAmplifier);

    audioEngine.playNote(10);

    EXPECT_EQ(oscillator1.getFrequency(), PITCH_FREQUENCIES[10]);
    EXPECT_EQ(oscillator2.getFrequency(), PITCH_FREQUENCIES[10]);
}
