#include "Synth.h"

jni::model::Synth::Synth(
        std::unique_ptr<synth::Envelope> ampEnvelope,
        std::unique_ptr<synth::AudioEngine> audioEngine,
        std::unique_ptr<synth::Oscillator> oscillator1,
        std::unique_ptr<synth::Oscillator> oscillator2,
        std::unique_ptr<synth::Filter> filter
) :
        ampEnvelope_(std::move(ampEnvelope)),
        audioEngine_(std::move(audioEngine)),
        oscillator1_(std::move(oscillator1)),
        oscillator2_(std::move(oscillator2)),
        filter_(std::move(filter)) {}

auto jni::model::Synth::getStream() -> synth::AudioStream & {
    return *stream_;
}

auto jni::model::Synth::fromPtr(jlong pointer) -> jni::model::Synth & {
    return *(reinterpret_cast<Synth *>(pointer)); // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
}

void jni::model::Synth::setStream(std::unique_ptr<synth::AudioStream> stream) {
    stream_ = std::move(stream);
}

auto jni::model::Synth::getAmpEnvelope() -> synth::Envelope & {
    return *ampEnvelope_;
}

auto jni::model::Synth::getAudioEngine() -> synth::AudioEngine & {
    return *audioEngine_;
}

auto jni::model::Synth::getOscillator1() -> synth::Oscillator & {
    return *oscillator1_;
}

auto jni::model::Synth::getOscillator2() -> synth::Oscillator & {
    return *oscillator2_;
}

auto jni::model::Synth::getFilter() -> synth::Filter & {
    return *filter_;
}

