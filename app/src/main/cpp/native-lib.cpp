#include "AudioEngine.h"
#include "AudioStream.h"
#include "Envelope.h"
#include "NoiseWaveform.h"
#include "Oscillator.h"
#include "SineWaveform.h"
#include "SquareWaveform.h"
#include "TriangleWaveform.h"
#include <jni.h>
#include <oboe/Oboe.h>
#include <string>

namespace synth {
    static std::unique_ptr<AudioStream> stream;
    static std::unique_ptr<Envelope> ampEnvelope;
    static std::unique_ptr<AudioEngine> audioEngine;
    static std::unique_ptr<Oscillator> osc1;
    static std::unique_ptr<Oscillator> osc2;

    enum WaveformType {
        Sine,
        Square,
        Triangle,
        Noise
    };

    auto createWaveform(WaveformType type) -> std::unique_ptr<Waveform> {
        std::unique_ptr<Waveform> waveform;
        switch (type) {
            case WaveformType::Sine:
                waveform = std::make_unique<SineWaveform>(type);
                break;
            case WaveformType::Triangle:
                waveform = std::make_unique<TriangleWaveform>(type);
                break;
            case WaveformType::Square:
                waveform = std::make_unique<SquareWaveform>(type);
                break;
            case WaveformType::Noise:
                waveform = std::make_unique<NoiseWaveform>(type);
                break;
            default:
                throw std::invalid_argument("No such waveform for type " + std::to_string(type));
        }
        return std::move(waveform);
    }

    auto getOscillatorFromId(
            JNIEnv *env,
            jobject nativeSynthOscillator
    ) -> Oscillator & {
        jclass cls = env->GetObjectClass(nativeSynthOscillator);
        jfieldID field = env->GetFieldID(cls, "oscillatorId", "I");
        int oscillatorId = env->GetIntField(nativeSynthOscillator, field);

        Oscillator *osc;
        switch (oscillatorId) {
            case 0:
                osc = osc1.get();
                break;
            case 1:
                osc = osc2.get();
                break;
            default:
                throw std::invalid_argument("No oscillator " + std::to_string(oscillatorId));
        }

        return *osc;
    }

    extern "C" {

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynth_initialize() {
        auto waveform1 = createWaveform(WaveformType::Sine);
        osc1 = std::make_unique<Oscillator>(AudioStream::getSampleRate(), std::move(waveform1));
        auto waveform2 = createWaveform(WaveformType::Sine);
        osc2 = std::make_unique<Oscillator>(AudioStream::getSampleRate(), std::move(waveform2));
        constexpr auto attack = 100.0F;
        constexpr auto decay = 100.0F;
        constexpr auto sustain = 0.3F;
        constexpr auto release = 4000.0F;
        EnvelopeParameters defaultEnvelopeParameters =
                {attack, decay, sustain, release};
        ampEnvelope = std::make_unique<Envelope>(
                AudioStream::getSampleRate(),
                defaultEnvelopeParameters
        );
        EnvelopeControlledAmplifier envelopeControlledAmplifier(*ampEnvelope);
        audioEngine = std::make_unique<AudioEngine>(
                *osc1,
                *osc2,
                envelopeControlledAmplifier
        );
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynth_getVersion(
            JNIEnv *env
    ) -> jstring {
        const std::string version = "0.1.0";
        return env->NewStringUTF(version.c_str());
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynth_start() {
        stream = std::make_unique<AudioStream>(*audioEngine);
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynth_stop() {
        stream->close();
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynth_playNote(
            JNIEnv */* env */,
            jclass /* cls */,
            jint pitch
    ) {
        audioEngine->playNote(pitch);
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynth_stopNote() {
        audioEngine->stopNote();
    }

    JNIEXPORT JNICALL auto
    Java_com_flatmapdev_synth_jni_NativeSynth_getAmpEnvelope(
            JNIEnv *env,
            jobject /* cls */
    ) -> jfloatArray {
        jfloatArray result;
        result = env->NewFloatArray(4);
        auto envelopeParameters = ampEnvelope->getEnvelopeParameters();
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
    Java_com_flatmapdev_synth_jni_NativeSynth_setAmpEnvelope(
            JNIEnv *env,
            jobject /* cls */,
            jfloatArray jEnvelopeAdsr
    ) {
        float *envelopeAdsr = env->GetFloatArrayElements(jEnvelopeAdsr, nullptr);
        EnvelopeParameters envelopeParameters{
                envelopeAdsr[0],
                envelopeAdsr[1],
                envelopeAdsr[2],
                envelopeAdsr[3]
        };
        ampEnvelope->setEnvelopeParameters(envelopeParameters);
    }
    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_getOscillator(
            JNIEnv *env,
            jobject obj
    ) -> jobject {
        Oscillator &osc = getOscillatorFromId(env, obj);
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
            jobject jOscillator
    ) -> void {
        Oscillator &osc = getOscillatorFromId(env, obj);
        jclass cls = env->GetObjectClass(jOscillator);
        jfieldID fid = env->GetFieldID(cls, "pitchOffset", "I");
        jint pitchOffset = env->GetIntField(jOscillator, fid);
        osc.setPitchOffset(pitchOffset);
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_setWaveform(
            JNIEnv *env,
            jobject obj,
            jint waveformTypeInt
    ) -> void {
        Oscillator &osc = getOscillatorFromId(env, obj);
        auto waveformType = static_cast<WaveformType>(waveformTypeInt);
        auto waveform = createWaveform(waveformType);
        osc.setWaveform(std::move(waveform));
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_setPitchOffset(
            JNIEnv *env,
            jobject obj,
            jint pitchOffset
    ) -> void {
        Oscillator &osc = getOscillatorFromId(env, obj);
        osc.setPitchOffset(pitchOffset);
    }

    } // extern "C"
} // namespace synth
