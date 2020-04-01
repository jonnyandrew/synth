#include "OscillatorJni.h"
#include "FilterJni.h"
#include "SynthEngineJni.h"
#include <jni.h>

namespace jni {
    extern "C" auto JNI_OnLoad(JavaVM *vm, void */*reserved*/) -> jint {
        JNIEnv *env;
        if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
            return JNI_ERR;
        }

        if (jni::registerSynthEngineMethods(env) == JNI_ERR) { return JNI_ERR; }
        if (jni::registerOscillatorMethods(env) == JNI_ERR) { return JNI_ERR; }
        if (jni::registerFilterMethods(env) == JNI_ERR) { return JNI_ERR; }

        return JNI_VERSION_1_6;
    }
}  // namespace jni