#include "Helpers.h"
#include <android/log.h>
#include <jni.h>
#include <nativehelper/JNIHelp.h>

auto jni::findClass(JNIEnv *env, const char *name) -> jclass {
    auto cls = env->FindClass(name);
    if (cls == nullptr) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG,
                            "Cannot find class %s", name);
    }
    return cls;
}

auto jni::getFieldID(JNIEnv *env, jclass clazz, const char *name, const char *sig) -> jfieldID {
    auto fieldId = env->GetFieldID(clazz, name, sig);
    if (fieldId == nullptr) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG,
                            "Cannot find field named %s with signature %s", name, sig);
    }
    return fieldId;
}

auto jni::getMethodID(JNIEnv *env, jclass clazz, const char *name, const char *sig) -> jmethodID {
    auto methodId = env->GetMethodID(clazz, name, sig);
    if (methodId == nullptr) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG,
                            "Cannot find method named %s with signature %s", name, sig);
    }
    return methodId;
}

