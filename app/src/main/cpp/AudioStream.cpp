#include "AudioStream.h"
#include "Constants.h"
#include "PlayException.h"
#include "AudioEngine.h"
#include <oboe/AudioStream.h>
#include <oboe/Oboe.h>
#include <android/log.h>

synth::AudioStream::AudioStream(synth::SignalSource &audioSource)
        : audioSource_(&audioSource) {
    oboe::AudioStreamBuilder builder;
    builder
            .setDirection(oboe::Direction::Output)
            ->setPerformanceMode(oboe::PerformanceMode::LowLatency)
            ->setSharingMode(oboe::SharingMode::Exclusive)
            ->setFormat(oboe::AudioFormat::Float)
            ->setChannelCount(oboe::ChannelCount::Mono)
            ->setCallback(this);

    const oboe::Result result = builder.openManagedStream(oboeStream_);

    if (result != oboe::Result::OK) {
        std::string resultText = oboe::convertToText(result);

        __android_log_print(
                ANDROID_LOG_ERROR, LOGGER_TAG,
                "Failed to create stream. Error: %s",
                resultText.c_str()
        );

        throw PlayException(resultText);
    }
    oboeStream_->requestStart();
}


auto synth::AudioStream::onAudioReady(
        oboe::AudioStream *oboeStream,
        void *audioData,
        int32_t numFrames
) -> oboe::DataCallbackResult {
    if (audioSource_ == nullptr) {
        return oboe::DataCallbackResult::Continue;
    }

    std::vector<float> buffer(static_cast<size_t>(numFrames));

    audioSource_->getSignal(buffer);

    std::copy(buffer.begin(), buffer.end(), static_cast<float *>(audioData));

    return oboe::DataCallbackResult::Continue;
}

auto synth::AudioStream::getSampleRate() -> int {
    if (sampleRate_ == 0) {
        oboe::ManagedStream tmpStream;
        oboe::AudioStreamBuilder().openManagedStream(tmpStream);
        sampleRate_ = tmpStream->getSampleRate();
        tmpStream->close();
    }

    return sampleRate_;
}

void synth::AudioStream::close() {
    oboeStream_->close();
}

int synth::AudioStream::sampleRate_;
