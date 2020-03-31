
#ifndef JNI_SYNTHENGINEJNI_H
#define JNI_SYNTHENGINEJNI_H

#include <jni.h>

namespace jni {
    jlong initialize(JNIEnv */* env */);

    void cleanUp(JNIEnv */* env */, jobject /* cls */, jlong synth);

    auto getVersion(JNIEnv *env) -> jstring;

    void start(JNIEnv */* env */, jclass /* cls */, jlong synthPtr);

    void stop(JNIEnv */* env */, jclass /* cls */, jlong synthPtr);

    void playNote(JNIEnv */* env */, jclass /* cls */, jlong synthPtr,
                  jint pitch
    );

    void stopNote(JNIEnv */* env */, jclass /* cls */, jlong synthPtr);

} // namespace jni
#endif // JNI_SYNTHENGINEJNI_H
