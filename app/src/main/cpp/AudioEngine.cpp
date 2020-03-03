#include "AudioEngine.h"
#include "AudioStream.h"
#include "Constants.h"
#include "Envelope.h"
#include "EnvelopeControlledAmplifier.h"
#include <android/log.h>
#include <oboe/AudioStream.h>
#include <oboe/Oboe.h>

synth::AudioEngine::AudioEngine(
        Oscillator &oscillator1,
        Oscillator &oscillator2,
        EnvelopeControlledAmplifier envelopeControlledAmplifier
) :
        oscillator1_(&oscillator1),
        oscillator2_(&oscillator2),
        envelopeControlledAmplifier_(std::move(envelopeControlledAmplifier)) {
    __android_log_print(ANDROID_LOG_INFO, LOGGER_TAG, "Initializing AudioEngine");
}

void synth::AudioEngine::playNote(const int32_t pitch) {
    oscillator1_->setPitch(pitch);
    oscillator2_->setPitch(pitch);
    envelopeControlledAmplifier_.startAttack();
}

void synth::AudioEngine::stopNote() {
    envelopeControlledAmplifier_.startRelease();
}

void synth::AudioEngine::getSignal(std::vector<float> &buffer) {
    const size_t numFrames = buffer.size();
    std::vector<float> audio1(numFrames);
    std::vector<float> audio2(numFrames);
    oscillator1_->render(audio1, numFrames);
    oscillator2_->render(audio2, numFrames);

    // mix
    for (int i = 0; i < numFrames; i++) {
        buffer[i] = (audio1[i] + audio2[i]) / 2;
    }

    // apply effects
    envelopeControlledAmplifier_.getSignal(buffer);
}
