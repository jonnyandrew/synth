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

    auto createWaveform(int type) -> std::unique_ptr<Waveform> {
        std::unique_ptr<Waveform> waveform;
        switch (type) {
            case 0:
                waveform = std::make_unique<SineWaveform>();
                break;
            case 1:
                waveform = std::make_unique<TriangleWaveform>();
                break;
            case 2:
                waveform = std::make_unique<SquareWaveform>();
                break;
            case 3:
                waveform = std::make_unique<NoiseWaveform>();
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
        if (field == NULL) {
            /* Handle error */;
        }
        int oscillatorId = env->GetIntField(nativeSynthOscillator, field);

        if (oscillatorId == 0) {
            return *osc1;
        } else if (oscillatorId == 1) {
            return *osc2;
        } else {
            throw std::invalid_argument("No oscillator" + std::to_string(oscillatorId));
        }
    }

    extern "C" {

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynth_initialize() {
        auto waveform1 = std::make_unique<SineWaveform>();
        osc1 = std::make_unique<Oscillator>(AudioStream::getSampleRate(), std::move(waveform1));
        auto waveform2 = std::make_unique<SineWaveform>();
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
        // Get the class we wish to return an instance of
        jclass clazz = env->FindClass("com/flatmapdev/synth/oscillatorCore/model/Oscillator");

        // Get the method id of an empty constructor in clazz
        jmethodID constructor = env->GetMethodID(clazz, "<init>", "(I)V");

        // Create an instance of clazz
        jobject result = env->NewObject(clazz, constructor, osc.getPitchOffset());

        // return object
        return result;
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
            jint waveformType
    ) -> void {
        Oscillator &osc = getOscillatorFromId(env, obj);
        auto waveform = createWaveform(waveformType);
        osc.setWaveform(std::move(waveform));
    }

    } // extern "C"
} // namespace synth
