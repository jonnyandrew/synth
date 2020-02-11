#include <oboe/AudioStream.h>
#include <oboe/Oboe.h>
#include <android/log.h>
#include "AudioEngine.h"
#include "Constants.h"

AudioEngine::AudioEngine(
        Oscillator &oscillator
) {

    __android_log_print(ANDROID_LOG_INFO, LOGGER_TAG, "Initializing AudioEngine");

    oscillator_ = &oscillator;
}

void AudioEngine::start() {
    oboe::AudioStreamBuilder builder;
    builder
            .setDirection(oboe::Direction::Output)
            ->setPerformanceMode(oboe::PerformanceMode::LowLatency)
            ->setSharingMode(oboe::SharingMode::Exclusive)
            ->setFormat(oboe::AudioFormat::Float)
            ->setChannelCount(oboe::ChannelCount::Mono)
            ->setCallback(this);

    oboe::Result result = builder.openManagedStream(stream_);

    if (result != oboe::Result::OK) {
        std::string resultText = oboe::convertToText(result);

        __android_log_print(
                ANDROID_LOG_ERROR, LOGGER_TAG,
                "Failed to create stream. Error: %s",
                resultText.c_str()
        );

        throw PlayException(resultText);
    }

    oscillator_->setSampleRate(stream_->getSampleRate());

    stream_->requestStart();
}

oboe::DataCallbackResult
AudioEngine::onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) {
    // We requested AudioFormat::Float so we assume we got it.
    // For production code always check what format
    // the stream has and cast to the appropriate type.
    auto *outputData = static_cast<float *>(audioData);

    oscillator_->render(outputData, numFrames);

    return oboe::DataCallbackResult::Continue;
}

void AudioEngine::playNote() {
    oscillator_->setWaveOn(true);
}

void AudioEngine::stopNote() {
    oscillator_->setWaveOn(false);
}
