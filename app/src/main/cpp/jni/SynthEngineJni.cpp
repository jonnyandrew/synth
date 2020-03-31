#include "SynthEngineJni.h"

#include "../synth/NoiseWaveform.h"
#include "../synth/SineWaveform.h"
#include "../synth/SquareWaveform.h"
#include "../synth/TriangleWaveform.h"
#include "model/Synth.h"
#include "model/WaveformType.h"
#include <array>
#include <nativehelper/JNIHelp.h>

namespace jni {
    extern "C" {
    auto JNI_OnLoad(JavaVM *vm, void */*reserved*/) -> jint {
        JNIEnv *env;
        if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
            return JNI_ERR;
        }

        jclass c = env->FindClass("com/flatmapdev/synth/jni/NativeSynthEngine");
        if (c == nullptr) { return JNI_ERR; }

        auto methods = std::vector<JNINativeMethod>{
                {
                        {"initialize", "()J", reinterpret_cast<void *>(jni::initialize)},
                        {"cleanUp", "(J)V", reinterpret_cast<void *>(jni::cleanUp)},
                        {"getVersion", "()Ljava/lang/String;", reinterpret_cast<void *>(jni::getVersion)},
                        {"start", "(J)V", reinterpret_cast<void *>(jni::start)},
                        {"stop", "(J)V", reinterpret_cast<void *>(jni::stop)},
                        {"playNote", "(JI)V", reinterpret_cast<void *>(jni::playNote)},
                        {"stopNote", "(J)V", reinterpret_cast<void *>(jni::stopNote)},
                }
        };

        jniRegisterNativeMethods(env,
                                 "com/flatmapdev/synth/jni/NativeSynthEngine",
                                 methods.data(),
                                 methods.size());

        return JNI_VERSION_1_6;
    }
    }

    auto initialize(
            JNIEnv */* env */
    ) -> jlong {
        auto waveform1 = jni::model::createWaveform(model::WaveformType::Sine);
        auto osc1 = std::make_unique<synth::Oscillator>(synth::AudioStream::getSampleRate(),
                                                        std::move(waveform1));
        auto waveform2 = jni::model::createWaveform(model::WaveformType::Sine);
        auto osc2 = std::make_unique<synth::Oscillator>(synth::AudioStream::getSampleRate(),
                                                        std::move(waveform2));
        constexpr auto attack = 100.0F;
        constexpr auto decay = 100.0F;
        constexpr auto sustain = 0.3F;
        constexpr auto release = 4000.0F;
        synth::EnvelopeParameters defaultEnvelopeParameters =
                {attack, decay, sustain, release};
        auto ampEnvelope = std::make_unique<synth::Envelope>(
                synth::AudioStream::getSampleRate(),
                defaultEnvelopeParameters
        );
        synth::EnvelopeControlledAmplifier envelopeControlledAmplifier(*ampEnvelope);
        auto filter = std::make_unique<synth::Filter>(synth::AudioStream::getSampleRate());
        auto audioEngine = std::make_unique<synth::AudioEngine>(
                *osc1,
                *osc2,
                envelopeControlledAmplifier,
                *filter
        );
        // NOLINTNEXTLINE(cppcoreguidelines-pro-type-reinterpret-cast)
        return reinterpret_cast<jlong>(new model::Synth(
                std::move(ampEnvelope),
                std::move(audioEngine),
                std::move(osc1),
                std::move(osc2),
                std::move(filter)
        ));
    }

    void cleanUp(
            JNIEnv */* env */,
            jobject /* cls */,
            jlong synth
    ) {
        delete &model::Synth::fromPtr(synth); // NOLINT(cppcoreguidelines-owning-memory)
    }

    auto getVersion(
            JNIEnv *env
    ) -> jstring {
        const std::string version = "0.1.0";
        return env->NewStringUTF(version.c_str());
    }

    void start(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        auto stream = std::make_unique<synth::AudioStream>(synth->getAudioEngine());
        synth->setStream(std::move(stream));
    }

    void stop(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getStream().close();
    }

    void playNote(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr,
            jint pitch
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getAudioEngine().playNote(pitch);
    }

    void stopNote(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getAudioEngine().stopNote();
    }
} // namespace jni
