#include <jni.h>
#include <string>
#include <oboe/Oboe.h>
#include "AudioEngine.h"
#include "AudioStream.h"
#include "Envelope.h"

using namespace synth;

std::unique_ptr<AudioStream> stream;
std::unique_ptr<AudioEngine> audioEngine;

extern "C" JNIEXPORT jstring JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_getVersion(
        JNIEnv *env
) {
    const std::string version = "0.1.0";
    return env->NewStringUTF(version.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_start() {
    stream = std::make_unique<AudioStream>();
    auto sampleRate = stream->getSampleRate();
    Oscillator osc1(sampleRate);
    Oscillator osc2(sampleRate);
    Envelope envelope = {sampleRate, 15, 400, 0.0, 1000};
    EnvelopeControlledAmplifier envelopeControlledAmplifier(envelope);
    audioEngine = std::make_unique<AudioEngine>(
            osc1,
            osc2,
            envelopeControlledAmplifier
    );
    stream->setAudioSource(*audioEngine);
    stream->start();
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
