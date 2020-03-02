#include "AudioEngine.h"
#include "AudioStream.h"
#include "Envelope.h"
#include <jni.h>
#include <oboe/Oboe.h>
#include <string>

static std::unique_ptr<synth::AudioStream> stream;
static std::unique_ptr<synth::Envelope> ampEnvelope;
static std::unique_ptr<synth::AudioEngine> audioEngine;

extern "C" {

JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_initialize() {
    synth::Oscillator osc1(synth::AudioStream::getSampleRate());
    synth::Oscillator osc2(synth::AudioStream::getSampleRate());
    constexpr auto attack = 100.0F;
    constexpr auto decay = 100.0F;
    constexpr auto sustain = 0.3F;
    constexpr auto release = 4000.0F;
    synth::EnvelopeParameters defaultEnvelopeParameters =
            {attack, decay, sustain, release};
    ampEnvelope = std::make_unique<synth::Envelope>(
            synth::AudioStream::getSampleRate(),
            defaultEnvelopeParameters
    );
    synth::EnvelopeControlledAmplifier envelopeControlledAmplifier(*ampEnvelope);
    audioEngine = std::make_unique<synth::AudioEngine>(
            osc1,
            osc2,
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
    stream = std::make_unique<synth::AudioStream>(*audioEngine);
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
        jclass /* cls */
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
        jclass /* cls */,
        jfloatArray jEnvelopeAdsr
) {
    float *envelopeAdsr = env->GetFloatArrayElements(jEnvelopeAdsr, nullptr);
    synth::EnvelopeParameters envelopeParameters{
            envelopeAdsr[0],
            envelopeAdsr[1],
            envelopeAdsr[2],
            envelopeAdsr[3]
    };
    ampEnvelope->setEnvelopeParameters(envelopeParameters);
}

}
