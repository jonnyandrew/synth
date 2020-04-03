#include "OscillatorJni.h"
#include "AmpEnvelopeJni.h"
#include "FilterJni.h"
#include "SynthEngineJni.h"
#include <jni.h>

namespace jni {
    extern "C" auto JNI_OnLoad(JavaVM *vm, void */*reserved*/) -> jint {
        void *envVoidPtr;
        if (vm->GetEnv(&envVoidPtr, JNI_VERSION_1_6) != JNI_OK) {
            return JNI_ERR;
        }
        auto *env = static_cast<JNIEnv *>(envVoidPtr);

        if (jni::registerAmpEnvelopeMethods(env) == JNI_ERR) { return JNI_ERR; }
        if (jni::setUpFilterJni(env) == JNI_ERR) { return JNI_ERR; }
        if (jni::setUpOscillatorJni(env) == JNI_ERR) { return JNI_ERR; }
        if (jni::registerSynthEngineMethods(env) == JNI_ERR) { return JNI_ERR; }

        return JNI_VERSION_1_6;
    }
}  // namespace jni