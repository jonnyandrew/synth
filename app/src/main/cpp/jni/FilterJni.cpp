#include "model/Synth.h"
#include <jni.h>

namespace jni {

    extern "C" {

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthFilter_getFilter(
            JNIEnv *env,
            jobject  /*obj*/,
            jlong synthPtr
    ) -> jobject {
        auto synth = &model::Synth::fromPtr(synthPtr);
        jclass filterClass = env->FindClass(
                "com/flatmapdev/synth/filterData/model/FilterData");
        jmethodID filterConstructor = env->GetMethodID(filterClass, "<init>", "(FF)V");
        return env->NewObject(filterClass, filterConstructor,
                              synth->getFilter().getCutoff(),
                              synth->getFilter().getResonance()
        );
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthFilter_setIsActive(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jboolean isActive
    ) -> void {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getFilter().setIsActive(static_cast<bool>(isActive));
    }


    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthFilter_setCutoff(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jfloat cutoffFrequency
    ) -> void {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getFilter().setCutoff(cutoffFrequency);
    }

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthFilter_setResonance(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jfloat resonance
    ) -> void {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getFilter().setResonance(resonance);
    }
    }
}  // namespace jni
