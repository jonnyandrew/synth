#include "../synth/AudioEngine.h"
#include "../synth/AudioStream.h"
#include "../synth/Envelope.h"
#include "../synth/Filter.h"
#include "../synth/NoiseWaveform.h"
#include "../synth/Oscillator.h"
#include "../synth/SineWaveform.h"
#include "../synth/SquareWaveform.h"
#include "../synth/TriangleWaveform.h"
#include "Synth.h"
#include <jni.h>
#include <oboe/Oboe.h>
#include <string>

namespace jni {
    enum WaveformType {
        Sine,
        Square,
        Triangle,
        Noise
    };

    auto getSynth(jlong pointer) -> Synth & {
        return *(reinterpret_cast<Synth *>(pointer)); // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
    }

    auto createWaveform(WaveformType type) -> std::unique_ptr<synth::Waveform> {
        std::unique_ptr<synth::Waveform> waveform;
        switch (type) {
            case WaveformType::Sine:
                waveform = std::make_unique<synth::SineWaveform>(type);
                break;
            case WaveformType::Triangle:
                waveform = std::make_unique<synth::TriangleWaveform>(type);
                break;
            case WaveformType::Square:
                waveform = std::make_unique<synth::SquareWaveform>(type);
                break;
            case WaveformType::Noise:
                waveform = std::make_unique<synth::NoiseWaveform>(type);
                break;
            default:
                throw std::invalid_argument("No such waveform for type " + std::to_string(type));
        }
        return std::move(waveform);
    }

    auto getOscillatorFromId(
            JNIEnv *env,
            jobject nativeSynthOscillator,
            jlong synthPtr
    ) -> synth::Oscillator & {
        auto synth = &getSynth(synthPtr);
        jclass cls = env->GetObjectClass(nativeSynthOscillator);
        jfieldID field = env->GetFieldID(cls, "oscillatorId", "I");
        int oscillatorId = env->GetIntField(nativeSynthOscillator, field);

        synth::Oscillator *osc;
        switch (oscillatorId) {
            case 0:
                osc = &synth->getOscillator1();
                break;
            case 1:
                osc = &synth->getOscillator2();
                break;
            default:
                throw std::invalid_argument("No oscillator " + std::to_string(oscillatorId));
        }

        return *osc;
    }

    extern "C" {

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_initialize(
            JNIEnv */* env */
    ) -> jlong {
        auto waveform1 = createWaveform(WaveformType::Sine);
        auto osc1 = std::make_unique<synth::Oscillator>(synth::AudioStream::getSampleRate(),
                                                        std::move(waveform1));
        auto waveform2 = createWaveform(WaveformType::Sine);
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
        return reinterpret_cast<jlong>(new Synth(
                std::move(ampEnvelope),
                std::move(audioEngine),
                std::move(osc1),
                std::move(osc2),
                std::move(filter)
        ));
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_cleanUp(
            JNIEnv */* env */,
            jobject /* cls */,
            jlong synth
    ) {
        delete &getSynth(synth);
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
        auto synth = &getSynth(synthPtr);
        auto stream = std::make_unique<synth::AudioStream>(synth->getAudioEngine());
        synth->setStream(std::move(stream));
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_stop(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr
    ) {
        auto synth = &getSynth(synthPtr);
        synth->getStream().close();
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_playNote(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr,
            jint pitch
    ) {
        auto synth = &getSynth(synthPtr);
        synth->getAudioEngine().playNote(pitch);
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_stopNote(
            JNIEnv */* env */,
            jclass /* cls */,
            jlong synthPtr
    ) {
        auto synth = &getSynth(synthPtr);
        synth->getAudioEngine().stopNote();
    }

    JNIEXPORT JNICALL auto
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_getAmpEnvelope(
            JNIEnv *env,
            jobject /* cls */,
            jlong synthPtr
    ) -> jfloatArray {
        auto synth = &getSynth(synthPtr);
        jfloatArray result;
        result = env->NewFloatArray(4);
        auto envelopeParameters = synth->getAmpEnvelope().getEnvelopeParameters();
        jfloat buffer[4]{
                envelopeParameters.attackTimeMs,
                envelopeParameters.decayTimeMs,
                envelopeParameters.sustainLevel,
                envelopeParameters.releaseTimeMs
        };
        env->SetFloatArrayRegion(result, 0, 4, buffer);
        return result;
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_setAmpEnvelope(
            JNIEnv *env,
            jobject /* cls */,
            jlong synthPtr,
            jfloatArray jEnvelopeAdsr
    ) {
        auto synth = &getSynth(synthPtr);
        float *envelopeAdsr = env->GetFloatArrayElements(jEnvelopeAdsr, nullptr);
        synth::EnvelopeParameters envelopeParameters{
                envelopeAdsr[0],
                envelopeAdsr[1],
                envelopeAdsr[2],
                envelopeAdsr[3]
        };
        synth->getAmpEnvelope().setEnvelopeParameters(envelopeParameters);
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_getOscillator(
            JNIEnv *env,
            jobject obj,
            jlong synth
    ) -> jobject {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        jclass oscillatorClass = env->FindClass(
                "com/flatmapdev/synth/oscillatorData/model/OscillatorData");
        jmethodID oscillatorConstructor = env->GetMethodID(oscillatorClass, "<init>", "(II)V");
        auto waveformType = static_cast<WaveformType>(osc.getWaveform().getLabel());
        return env->NewObject(oscillatorClass, oscillatorConstructor,
                              osc.getPitchOffset(),
                              static_cast<jint>(waveformType)
        );
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_setOscillator(
            JNIEnv *env,
            jobject obj,
            jlong synth,
            jobject jOscillator
    ) -> void {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        jclass cls = env->GetObjectClass(jOscillator);
        jfieldID fid = env->GetFieldID(cls, "pitchOffset", "I");
        jint pitchOffset = env->GetIntField(jOscillator, fid);
        osc.setPitchOffset(pitchOffset);
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_setWaveform(
            JNIEnv *env,
            jobject obj,
            jlong synth,
            jint waveformTypeInt
    ) -> void {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        auto waveformType = static_cast<WaveformType>(waveformTypeInt);
        auto waveform = createWaveform(waveformType);
        osc.setWaveform(std::move(waveform));
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_setPitchOffset(
            JNIEnv *env,
            jobject obj,
            jlong synth,
            jint pitchOffset
    ) -> void {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        osc.setPitchOffset(pitchOffset);
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthFilter_getFilter(
            JNIEnv *env,
            jobject  /*obj*/,
            jlong synthPtr
    ) -> jobject {
        auto synth = &getSynth(synthPtr);
        jclass filterClass = env->FindClass(
                "com/flatmapdev/synth/filterData/model/FilterData");
        jmethodID filterConstructor = env->GetMethodID(filterClass, "<init>", "(FF)V");
        return env->NewObject(filterClass, filterConstructor,
                              synth->getFilter().getCutoff(),
                              synth->getFilter().getResonance()
        );
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthFilter_setIsActive(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jboolean isActive
    ) -> void {
        auto synth = &getSynth(synthPtr);
        synth->getFilter().setIsActive(static_cast<bool>(isActive));
    }


    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthFilter_setCutoff(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jfloat cutoffFrequency
    ) -> void {
        auto synth = &getSynth(synthPtr);
        synth->getFilter().setCutoff(cutoffFrequency);
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthFilter_setResonance(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jfloat resonance
    ) -> void {
        auto synth = &getSynth(synthPtr);
        synth->getFilter().setResonance(resonance);
    }

    } // extern "C"
} // namespace jni
