#include "AudioEngine.h"
#include "AudioStream.h"
#include "Envelope.h"
#include "Oscillator.h"
#include <jni.h>
#include <oboe/Oboe.h>
#include <string>

namespace synth {
    extern "C" {

    static std::unique_ptr<AudioStream> stream;
    static std::unique_ptr<Envelope> ampEnvelope;
    static std::unique_ptr<AudioEngine> audioEngine;
    static std::unique_ptr<Oscillator> osc1;
    static std::unique_ptr<Oscillator> osc2;

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynth_initialize() {
        osc1 = std::make_unique<Oscillator>(AudioStream::getSampleRate());
        osc2 = std::make_unique<Oscillator>(AudioStream::getSampleRate());
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
    Java_com_flatmapdev_synth_jni_FirstNativeSynthOscillator_getOscillator(
            JNIEnv *env,
            jobject/* cls */
    ) -> jobject {
        // Get the class we wish to return an instance of
        jclass clazz = env->FindClass("com/flatmapdev/synth/oscillatorCore/model/Oscillator");

        // Get the method id of an empty constructor in clazz
        jmethodID constructor = env->GetMethodID(clazz, "<init>", "(I)V");

        // Create an instance of clazz
        jobject obj = env->NewObject(clazz, constructor, osc1->getPitchOffset());

        // return object
        return obj;
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_SecondNativeSynthOscillator_getOscillator(
            JNIEnv *env,
            jobject/* cls */
    ) -> jobject {
        // Get the class we wish to return an instance of
        jclass clazz = env->FindClass("com/flatmapdev/synth/oscillatorCore/model/Oscillator");

        // Get the method id of an empty constructor in clazz
        jmethodID constructor = env->GetMethodID(clazz, "<init>", "(I)V");

        // Create an instance of clazz
        jobject obj = env->NewObject(clazz, constructor, osc2->getPitchOffset());

        // return object
        return obj;
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_FirstNativeSynthOscillator_setOscillator(
            JNIEnv *env,
            jobject/* cls */,
            jobject jOscillator
    ) -> void {
        jclass cls = env->GetObjectClass(jOscillator);
        jfieldID fid = env->GetFieldID(cls, "pitchOffset", "I");
        jint pitchOffset = env->GetIntField(jOscillator, fid);
        osc1->setPitchOffset(pitchOffset);
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_SecondNativeSynthOscillator_setOscillator(
            JNIEnv *env,
            jobject/* cls */,
            jobject jOscillator
    ) -> void {
        jclass cls = env->GetObjectClass(jOscillator);
        jfieldID fid = env->GetFieldID(cls, "pitchOffset", "I");
        jint pitchOffset = env->GetIntField(jOscillator, fid);
        osc2->setPitchOffset(pitchOffset);
    }

    } // extern "C"
} // namespace synth
