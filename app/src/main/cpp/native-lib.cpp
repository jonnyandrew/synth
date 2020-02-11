#include <jni.h>
#include <string>
#include <oboe/Oboe.h>
#include "AudioEngine.h"

Oscillator osc;
AudioEngine audioEngine(osc);

extern "C" JNIEXPORT jstring JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_getVersion(
        JNIEnv *env
) {
    std::string version = "0.1.0";
    return env->NewStringUTF(version.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_start() {
    audioEngine.start();
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_playNote() {
    audioEngine.playNote();
}

extern "C" JNIEXPORT void JNICALL
Java_com_flatmapdev_synth_jni_NativeSynth_stopNote() {
    audioEngine.stopNote();
}
