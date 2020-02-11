#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_flatmapdev_synth_jni_Synth_getVersion(
        JNIEnv *env,
        jobject /* this */) {
    std::string version = "0.1.0";
    return env->NewStringUTF(version.c_str());
}
