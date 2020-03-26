#include "model/Synth.h"
#include "../synth/EnvelopeParameters.h"
#include <nativehelper/ScopedPrimitiveArray.h>

namespace jni {

    extern "C" {
    JNIEXPORT JNICALL auto
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_getAmpEnvelope(
            JNIEnv *env,
            jobject /* cls */,
            jlong synthPtr
    ) -> jfloatArray {
        auto synth = &model::Synth::fromPtr(synthPtr);
        jfloatArray result;
        result = env->NewFloatArray(4);
        auto envelopeParameters = synth->getAmpEnvelope().getEnvelopeParameters();
        jfloat buffer[4]{
                envelopeParameters.attackTimeMs,
                envelopeParameters.decayTimeMs,
                envelopeParameters.sustainLevel,
                envelopeParameters.releaseTimeMs
        };
        env->SetFloatArrayRegion(result, 0, 4, buffer);
        return result;
    }

    JNIEXPORT void JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthEngine_setAmpEnvelope(
            JNIEnv *env,
            jobject /* cls */,
            jlong synthPtr,
            jfloatArray jEnvelopeAdsr
    ) {
        auto synth = &model::Synth::fromPtr(synthPtr);
        ScopedFloatArrayRO envelopeAdsr(env, jEnvelopeAdsr);
        if (envelopeAdsr.get() == nullptr) {
            jniThrowNullPointerException(env, "envelope is null");
        }
        synth::EnvelopeParameters envelopeParameters{
                envelopeAdsr[0],
                envelopeAdsr[1],
                envelopeAdsr[2],
                envelopeAdsr[3]
        };
        synth->getAmpEnvelope().setEnvelopeParameters(envelopeParameters);
    }

    }
}  // namespace jni
