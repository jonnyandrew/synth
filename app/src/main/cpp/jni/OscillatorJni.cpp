#include "OscillatorJni.h"
#include "model/Synth.h"
#include "model/WaveformType.h"
#include <nativehelper/JNIHelp.h>

namespace jni {
    auto getOscillator(JNIEnv *env, jobject obj, jlong synth) -> jobject;

    auto setOscillator(JNIEnv *env, jobject obj, jlong synth, jobject jOscillator) -> void;

    auto setWaveform(JNIEnv *env, jobject obj, jlong synth, jint waveformTypeInt) -> void;

    auto setPitchOffset(JNIEnv *env, jobject obj, jlong synth, jint pitchOffset) -> void;

    auto registerOscillatorMethods(JNIEnv *env) -> jint {
        jclass c = env->FindClass("com/flatmapdev/synth/jni/NativeSynthOscillator");
        if (c == nullptr) { return JNI_ERR; }

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

        return JNI_VERSION_1_6;
    }

    auto getOscillatorFromId(
            JNIEnv *env,
            jobject nativeSynthOscillator,
            jlong synthPtr
    ) -> synth::Oscillator & {
        auto synth = &model::Synth::fromPtr(synthPtr);
        jclass cls = env->GetObjectClass(nativeSynthOscillator);
        jfieldID field = env->GetFieldID(cls, "oscillatorId", "I");
        int oscillatorId = env->GetIntField(nativeSynthOscillator, field);

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
        jclass oscillatorClass = env->FindClass(
                "com/flatmapdev/synth/oscillatorData/model/OscillatorData");
        jmethodID oscillatorConstructor = env->GetMethodID(oscillatorClass, "<init>", "(II)V");
        auto waveformType = static_cast<model::WaveformType>(osc.getWaveform().getLabel());
        return env->NewObject(oscillatorClass, oscillatorConstructor,
                              osc.getPitchOffset(),
                              static_cast<jint>(waveformType)
        );
    }

    auto setOscillator(
            JNIEnv *env,
            jobject obj,
            jlong synth,
            jobject jOscillator
    ) -> void {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        jclass cls = env->GetObjectClass(jOscillator);
        jfieldID fid = env->GetFieldID(cls, "pitchOffset", "I");
        jint pitchOffset = env->GetIntField(jOscillator, fid);
        osc.setPitchOffset(pitchOffset);
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

