#include <oboe/AudioStream.h>
#include <oboe/Oboe.h>
#include <android/log.h>
#include "AudioEngine.h"
#include "Constants.h"
#include "AudioStream.h"
#include "Envelope.h"
#include "EnvelopeControlledAmplifier.h"

synth::AudioEngine::AudioEngine(
        Oscillator oscillator1,
        Oscillator oscillator2,
        EnvelopeControlledAmplifier envelopeControlledAmplifier
) :
        oscillator1_(oscillator1),
        oscillator2_(oscillator2),
        envelopeControlledAmplifier_(envelopeControlledAmplifier) {
    __android_log_print(ANDROID_LOG_INFO, LOGGER_TAG, "Initializing AudioEngine");
}

void synth::AudioEngine::playNote(const int32_t pitch) {
    oscillator1_.setPitch(pitch);
    oscillator2_.setPitch(pitch + 4);
    envelopeControlledAmplifier_.startAttack();
}

void synth::AudioEngine::stopNote() {
    envelopeControlledAmplifier_.startRelease();
}

void synth::AudioEngine::getSignal(float *audioBuffer, const int numFrames) {
    float audio1[numFrames];
    float audio2[numFrames];
    oscillator1_.render(audio1, numFrames);
    oscillator2_.render(audio2, numFrames);

    // mix
    float mixed[numFrames];
    for (int i = 0; i < numFrames; i++) {
        mixed[i] = (audio1[i] + audio2[i]) / 2;
    }

    // apply effects
    envelopeControlledAmplifier_.getSignal(mixed, numFrames);

    // output
    for (int i = 0; i < numFrames; i++) {
        audioBuffer[i] = mixed[i];
    }
}
