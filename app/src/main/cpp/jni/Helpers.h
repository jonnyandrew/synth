#ifndef JNI_HELPERS_H
#define JNI_HELPERS_H

#include <jni.h>

namespace jni {
    constexpr auto LOG_TAG = "CppJni";

    jclass findClass(JNIEnv *env, const char *name);

    jfieldID getFieldID(JNIEnv *env, jclass clazz, const char *name, const char *sig);

    jmethodID getMethodID(JNIEnv *env, jclass clazz, const char *name, const char *sig);
}

#endif //JNI_HELPERS_H
