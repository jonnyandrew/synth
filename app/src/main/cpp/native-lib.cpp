#include <jni.h>
#include <string>
#include <oboe/Oboe.h>
#include "AudioEngine.h"
#include "AudioStream.h"
#include "Envelope.h"

using namespace synth;

static std::unique_ptr<AudioStream> stream;
static std::unique_ptr<Envelope> ampEnvelope;
static std::unique_ptr<AudioEngine> audioEngine;

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_initialize() {
    Oscillator osc1(AudioStream::getSampleRate());
    Oscillator osc2(AudioStream::getSampleRate());
    const auto attack = 100.0F;
    const auto decay = 100.0F;
    const auto sustain = 0.3F;
    const auto release = 4000.0F;
    EnvelopeParameters defaultEnvelopeParameters =
            {attack, decay, sustain, release};
    ampEnvelope = std::make_unique<Envelope>(
            AudioStream::getSampleRate(),
            defaultEnvelopeParameters
    );
    EnvelopeControlledAmplifier envelopeControlledAmplifier(*ampEnvelope);
    audioEngine = std::make_unique<AudioEngine>(
            osc1,
            osc2,
            envelopeControlledAmplifier
    );
}

extern "C" JNIEXPORT auto JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_getVersion(
        JNIEnv *env
) -> jstring {
    const std::string version = "0.1.0";
    return env->NewStringUTF(version.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_start() {
    stream = std::make_unique<AudioStream>(*audioEngine);
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_stop() {
    stream->close();
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_playNote(
        JNIEnv *env,
        jclass cls,
        jint pitch
) {
    audioEngine->playNote(pitch);
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_stopNote() {
    audioEngine->stopNote();
}

extern "C" JNIEXPORT JNICALL auto
Java_com_flatmapdev_synth_jni_NativeSynth_getAmpEnvelope(
        JNIEnv *env,
        jclass cls
) -> jfloatArray {
    jfloatArray result;
    result = env->NewFloatArray(4);
    EnvelopeParameters envelopeParameters = ampEnvelope->getEnvelopeParameters();
    jfloat buffer[4] = {
            envelopeParameters.attackTimeMs,
            envelopeParameters.decayTimeMs,
            envelopeParameters.sustainLevel,
            envelopeParameters.releaseTimeMs
    };
    env->SetFloatArrayRegion(result, 0, 4, buffer);
    return result;
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_setAmpEnvelope(
        JNIEnv *env,
        jclass cls,
        jfloatArray jEnvelopeAdsr
) {
    float *envelopeAdsr = env->GetFloatArrayElements(jEnvelopeAdsr, nullptr);
    EnvelopeParameters envelopeParameters = {
            envelopeAdsr[0],
            envelopeAdsr[1],
            envelopeAdsr[2],
            envelopeAdsr[3]
    };
    ampEnvelope->setEnvelopeParameters(envelopeParameters);
}

