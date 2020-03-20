#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <vector>
#include "synth/Pitch.h"
#include "synth/SineWaveform.h"
#include "synth/Filter.h"
#include "synth/Oscillator.h"
#include "synth/Constants.h"
#include "synth/AudioEngine.h"

using namespace synth;

TEST(AudioEngineTest, WhenKeyIsPressedItSetsTheOscillatorPitches) {
    constexpr auto sampleRate = 1000;
    auto sineWaveform1 = std::make_unique<SineWaveform>();
    Oscillator oscillator1{sampleRate, std::move(sineWaveform1)};
    auto sineWaveform2 = std::make_unique<SineWaveform>();
    Oscillator oscillator2{sampleRate, std::move(sineWaveform2)};
    Envelope envelope{sampleRate, {100, 100, 0.4, 100}};
    EnvelopeControlledAmplifier envelopeControlledAmplifier{envelope};
    Filter filter(sampleRate);
    AudioEngine audioEngine(oscillator1, oscillator2, envelopeControlledAmplifier, filter);

    audioEngine.playNote(10);

    EXPECT_EQ(oscillator1.getFrequency(), PITCH_FREQUENCIES[10]);
    EXPECT_EQ(oscillator2.getFrequency(), PITCH_FREQUENCIES[10]);
}
