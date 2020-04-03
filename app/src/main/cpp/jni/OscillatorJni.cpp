#include "OscillatorJni.h"
#include "Helpers.h"
#include "model/Synth.h"
#include "model/WaveformType.h"
#include <nativehelper/JNIHelp.h>

namespace jni {
    struct NativeOscillatorClassData {
        jfieldID oscillatorId;
    } classData;

    struct OscillatorDataClassData {
        jclass cls;
        jmethodID constructor;
        jfieldID pitchOffset;
        jfieldID waveformType;
    } oscillatorDataClassData;

    auto getOscillator(JNIEnv *env, jobject obj, jlong synth) -> jobject;

    auto setOscillator(JNIEnv *env, jobject obj, jlong synth, jobject jOscillatorData) -> void;

    auto setWaveform(JNIEnv *env, jobject obj, jlong synth, jint waveformTypeInt) -> void;

    auto setPitchOffset(JNIEnv *env, jobject obj, jlong synth, jint pitchOffset) -> void;

    auto setUpOscillatorJni(JNIEnv *env) -> jint {
        jclass cls = findClass(env, "com/flatmapdev/synth/jni/NativeSynthOscillator");
        if (cls == nullptr) { return JNI_ERR; }

        std::vector<JNINativeMethod> methods{
                {"getOscillator",  "(J)Lcom/flatmapdev/synth/oscillatorData/model/OscillatorData;",  reinterpret_cast<void *>(jni::getOscillator)}, // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
                {"setOscillator",  "(JLcom/flatmapdev/synth/oscillatorData/model/OscillatorData;)V", reinterpret_cast<void *>(jni::setOscillator)}, // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
                {"setWaveform",    "(JI)V",                                                          reinterpret_cast<void *>(jni::setWaveform)}, // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
                {"setPitchOffset", "(JI)V",                                                          reinterpret_cast<void *>(jni::setPitchOffset)}, // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
        };
        jniRegisterNativeMethods(
                env,
                "com/flatmapdev/synth/jni/NativeSynthOscillator",
                methods.data(),
                methods.size()

        );

        classData.oscillatorId = getFieldID(env, cls, "oscillatorId", "I");
        if (classData.oscillatorId == nullptr) { return JNI_ERR; }

        oscillatorDataClassData.cls = reinterpret_cast<jclass>( // NOLINT(cppcoreguidelines-pro-type-reinterpret-cast)
                env->NewGlobalRef(findClass(env,
                                            "com/flatmapdev/synth/oscillatorData/model/OscillatorData"
                ))
        );
        if (oscillatorDataClassData.cls == nullptr) { return JNI_ERR; }
        oscillatorDataClassData.constructor = getMethodID(env, oscillatorDataClassData.cls,
                                                          "<init>", "(II)V");
        if (oscillatorDataClassData.constructor == nullptr) { return JNI_ERR; }
        oscillatorDataClassData.pitchOffset = getFieldID(env, oscillatorDataClassData.cls,
                                                         "pitchOffset",
                                                         "I");
        if (oscillatorDataClassData.pitchOffset == nullptr) { return JNI_ERR; }
        oscillatorDataClassData.waveformType = getFieldID(env, oscillatorDataClassData.cls,
                                                          "waveformType",
                                                          "I");
        if (oscillatorDataClassData.waveformType == nullptr) { return JNI_ERR; }

        return JNI_VERSION_1_6;
    }

    auto getOscillatorFromId(
            JNIEnv *env,
            jobject nativeSynthOscillator,
            jlong synthPtr
    ) -> synth::Oscillator & {
        auto synth = &model::Synth::fromPtr(synthPtr);
        int oscillatorId = env->GetIntField(nativeSynthOscillator, classData.oscillatorId);

        synth::Oscillator *osc;
        switch (oscillatorId) {
            case 0:
                osc = &synth->getOscillator1();
                break;
            case 1:
                osc = &synth->getOscillator2();
                break;
            default:
                throw std::invalid_argument("No oscillator " + std::to_string(oscillatorId));
        }

        return *osc;
    }

    auto getOscillator(
            JNIEnv *env,
            jobject obj,
            jlong synth
    ) -> jobject {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        auto waveformType = static_cast<model::WaveformType>(osc.getWaveform().getLabel());
        return env->NewObject(oscillatorDataClassData.cls, oscillatorDataClassData.constructor,
                              osc.getPitchOffset(),
                              static_cast<jint>(waveformType)
        );
    }

    auto setOscillator(
            JNIEnv *env,
            jobject obj,
            jlong synth,
            jobject jOscillatorData
    ) -> void {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);

        jint pitchOffset = env->GetIntField(jOscillatorData, oscillatorDataClassData.pitchOffset);
        setPitchOffset(env, obj, synth, pitchOffset);

        jint waveformType = env->GetIntField(jOscillatorData, oscillatorDataClassData.waveformType);
        setWaveform(env, obj, synth, waveformType);
    }

    auto setWaveform(
            JNIEnv *env,
            jobject obj,
            jlong synth,
            jint waveformTypeInt
    ) -> void {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        auto waveformType = static_cast<model::WaveformType>(waveformTypeInt);
        auto waveform = createWaveform(waveformType);
        osc.setWaveform(std::move(waveform));
    }

    auto setPitchOffset(
            JNIEnv *env,
            jobject obj,
            jlong synth,
            jint pitchOffset
    ) -> void {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        osc.setPitchOffset(pitchOffset);
    }

}  // namespace jni

