#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_flatmapdev_synth_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Wow from C++";
    return env->NewStringUTF(hello.c_str());
}
