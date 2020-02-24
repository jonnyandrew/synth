#include <jni.h>
#include <string>
#include <oboe/Oboe.h>
#include "AudioEngine.h"
#include "AudioStream.h"
#include "Envelope.h"

using namespace synth;

AudioStream stream;
Oscillator osc1(stream.getSampleRate());
Oscillator osc2(stream.getSampleRate());
Envelope envelope = {stream.getSampleRate(), 15, 400, 0.0, 1000};
EnvelopeControlledAmplifier envelopeControlledAmplifier(envelope);

AudioEngine audioEngine(
        osc1,
        osc2,
        envelopeControlledAmplifier
);

extern "C" JNIEXPORT jstring JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_getVersion(
        JNIEnv *env
) {
    const std::string version = "0.1.0";
    return env->NewStringUTF(version.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_start() {
    stream.start(audioEngine);
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_stop() {
    stream.close();
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_playNote(
        JNIEnv *env,
        jclass cls,
        jint pitch
) {
    audioEngine.playNote(pitch);
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_stopNote() {
    audioEngine.stopNote();
}
