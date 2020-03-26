#include "SynthEngineJni.h"

#include "../synth/NoiseWaveform.h"
#include "../synth/SineWaveform.h"
#include "../synth/SquareWaveform.h"
#include "../synth/TriangleWaveform.h"
#include "model/Synth.h"
#include "model/WaveformType.h"
#include <nativehelper/JNIHelp.h>

namespace jni {
    extern "C" {

    JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void */*reserved*/) {
        JNIEnv *env;
        if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
            return JNI_ERR;
        }

        // Find your class. JNI_OnLoad is called from the correct class loader context for this to work.
        jclass c = env->FindClass("com/flatmapdev/synth/jni/NativeSynthEngine");
        if (c == nullptr) return JNI_ERR;

        jniThrowNullPointerException(env, "Class not found");

        JNINativeMethod methods[] = {
                {"initialise", "([I)[I", (void *) jni::initialise}
        };
        jniRegisterNativeMethods(env,
                                 "com/flatmapdev/synth/jni/NativeSynthEngine", methods,
                                 NELEM(methods));

        return JNI_VERSION_1_6;
    }
    }

    auto initialise(
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

    extern "C" {
    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_cleanUp(
            JNIEnv */* env */,
            jobject /* cls */,
            jlong synth
    ) {
        delete &model::Synth::fromPtr(synth); // NOLINT(cppcoreguidelines-owning-memory)
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_getVersion(
            JNIEnv *env
    ) -> jstring {
        const std::string version = "0.1.0";
        return env->NewStringUTF(version.c_str());
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_start(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        auto stream = std::make_unique<synth::AudioStream>(synth->getAudioEngine());
        synth->setStream(std::move(stream));
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_stop(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getStream().close();
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_playNote(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr,
            jint pitch
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getAudioEngine().playNote(pitch);
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_stopNote(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getAudioEngine().stopNote();
    }

    }
} // namespace jni
