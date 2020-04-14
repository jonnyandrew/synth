#include "model/Synth.h"
#include "Helpers.h"
#include <jni.h>
#include <nativehelper/JNIHelp.h>

namespace jni {
    struct FilterDataClassData {
        jclass cls;
        jmethodID constructor;
    } filterDataClassData;

    auto getFilter(JNIEnv *env, jobject /*unused*/, jlong synthPtr) -> jobject;

    void setIsActive(JNIEnv * /*unused*/, jobject /*unused*/, jlong synthPtr, jboolean isActive);

    void setCutoff(JNIEnv * /*unused*/, jobject /*unused*/, jlong synthPtr, jfloat cutoffFrequency);

    void setResonance(JNIEnv * /*unused*/, jobject /*unused*/, jlong synthPtr, jfloat resonance);

    auto setUpFilterJni(JNIEnv *env) -> jint {
        jclass c = env->FindClass("com/flatmapdev/synth/jni/NativeSynthFilter");
        if (c == nullptr) { return JNI_ERR; }

        std::vector<JNINativeMethod> methods{
                {"getFilter",    "(J)Lcom/flatmapdev/synth/filterData/model/FilterData;", reinterpret_cast<void *>(jni::getFilter)}, // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
                {"setIsActive",  "(JZ)V",                                                 reinterpret_cast<void *>(jni::setIsActive)}, // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
                {"setCutoff",    "(JF)V",                                                 reinterpret_cast<void *>(jni::setCutoff)}, // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
                {"setResonance", "(JF)V",                                                 reinterpret_cast<void *>(jni::setResonance)}, // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
        };

        jniRegisterNativeMethods(
                env,
                "com/flatmapdev/synth/jni/NativeSynthFilter",
                methods.data(),
                methods.size()
        );
        filterDataClassData.cls = reinterpret_cast<jclass>(// NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
                env->NewGlobalRef(findClass(
                        env, "com/flatmapdev/synth/filterData/model/FilterData"
                ))
        );
        if (filterDataClassData.cls == nullptr) { return JNI_ERR; }
        filterDataClassData.constructor = getMethodID(env, filterDataClassData.cls, "<init>",
                                                      "(FF)V");
        if (filterDataClassData.constructor == nullptr) { return JNI_ERR; }

        return JNI_VERSION_1_6;
    }

    auto getFilter(
            JNIEnv *env,
            jobject  /*obj*/,
            jlong synthPtr
    ) -> jobject {
        auto synth = &model::Synth::fromPtr(synthPtr);
        return env->NewObject(filterDataClassData.cls, filterDataClassData.constructor,
                              synth->getFilter().getCutoff(),
                              synth->getFilter().getResonance()
        );
    }

    auto setIsActive(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jboolean isActive
    ) -> void {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getFilter().setIsActive(static_cast<bool>(isActive));
    }

    auto setCutoff(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jfloat cutoffFrequency
    ) -> void {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getFilter().setCutoff(cutoffFrequency);
    }

    auto setResonance(
            JNIEnv * /*env*/,
            jobject  /*obj*/,
            jlong synthPtr,
            jfloat resonance
    ) -> void {
        auto synth = &model::Synth::fromPtr(synthPtr);
        synth->getFilter().setResonance(resonance);
    }
}  // namespace jni
