#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <vector>
#include <Pitch.h>
#include "Oscillator.h"
#include "Constants.h"
#include "../../main/cpp/AudioEngine.h"

using namespace synth;

TEST(AudioEngineTest, WhenKeyIsPressedItSetsTheOscillatorPitches) {
    constexpr auto sampleRate = 1000;
    Oscillator oscillator1{sampleRate};
    Oscillator oscillator2{sampleRate};
    Envelope envelope{sampleRate, {100, 100, 0.4, 100}};
    EnvelopeControlledAmplifier envelopeControlledAmplifier{envelope};
    AudioEngine audioEngine(oscillator1, oscillator2, envelopeControlledAmplifier);

    audioEngine.playNote(10);

    EXPECT_EQ(oscillator1.getFrequency(), PITCH_FREQUENCIES[10]);
    EXPECT_EQ(oscillator2.getFrequency(), PITCH_FREQUENCIES[10]);
}
