#include "model/Synth.h"
#include "model/WaveformType.h"

namespace jni {
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

    extern "C" {

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_getOscillator(
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

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_setOscillator(
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

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_setWaveform(
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

    JNIEXPORT auto JNICALL
    Java_com_flatmapdev_synth_jni_NativeSynthOscillator_setPitchOffset(
            JNIEnv *env,
            jobject obj,
            jlong synth,
            jint pitchOffset
    ) -> void {
        synth::Oscillator &osc = getOscillatorFromId(env, obj, synth);
        osc.setPitchOffset(pitchOffset);
    }

    }
}  // namespace jni

