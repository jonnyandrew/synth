#include "model/Synth.h"
#include "../synth/EnvelopeParameters.h"
#include <nativehelper/ScopedPrimitiveArray.h>

namespace jni {
    auto getAmpEnvelope(JNIEnv *env, jobject /*unused*/, jlong synthPtr) -> jfloatArray;

    void setAmpEnvelope(JNIEnv *env, jobject /*unused*/, jlong synthPtr, jfloatArray jEnvelopeAdsr);

    auto registerAmpEnvelopeMethods(JNIEnv *env) -> jint {
        jclass c = env->FindClass("com/flatmapdev/synth/jni/NativeSynthFilter");
        if (c == nullptr) { return JNI_ERR; }

        std::vector<JNINativeMethod> methods{
                {"getAmpEnvelope", "(J)[F",  reinterpret_cast<void *>(jni::getAmpEnvelope)},
                {"setAmpEnvelope", "(J[F)V", reinterpret_cast<void *>(jni::setAmpEnvelope)}
        };

        jniRegisterNativeMethods(
                env,
                "com/flatmapdev/synth/jni/NativeSynthEngine",
                methods.data(),
                methods.size()
        );

        return JNI_VERSION_1_6;
    }

    auto getAmpEnvelope(
            JNIEnv *env,
            jobject /* cls */,
            jlong synthPtr
    ) -> jfloatArray {
        auto synth = &model::Synth::fromPtr(synthPtr);
        jfloatArray result;
        result = env->NewFloatArray(4);
        auto envelopeParameters = synth->getAmpEnvelope().getEnvelopeParameters();
        std::vector<jfloat> buffer{
                envelopeParameters.attackTimeMs,
                envelopeParameters.decayTimeMs,
                envelopeParameters.sustainLevel,
                envelopeParameters.releaseTimeMs
        };
        env->SetFloatArrayRegion(result, 0, 4, buffer.data());
        return result;
    }

    void setAmpEnvelope(
            JNIEnv *env,
            jobject /* cls */,
            jlong synthPtr,
            jfloatArray jEnvelopeAdsr
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        ScopedFloatArrayRO envelopeAdsr(env, jEnvelopeAdsr);
        synth::EnvelopeParameters envelopeParameters{
                envelopeAdsr[0],
                envelopeAdsr[1],
                envelopeAdsr[2],
                envelopeAdsr[3]
        };
        synth->getAmpEnvelope().setEnvelopeParameters(envelopeParameters);
    }
}  // namespace jni
