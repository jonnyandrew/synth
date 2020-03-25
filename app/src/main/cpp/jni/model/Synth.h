#ifndef JNI_COMPONENTS_H
#define JNI_COMPONENTS_H

#include <memory>
#include <jni.h>

#include "../../synth/Oscillator.h"
#include "../../synth/AudioStream.h"
#include "../../synth/Envelope.h"
#include "../../synth/AudioEngine.h"
#include "../../synth/Filter.h"

namespace jni {
    namespace model {
        class Synth {
        public:
            Synth(
                    std::unique_ptr<synth::Envelope> ampEnvelope,
                    std::unique_ptr<synth::AudioEngine> audioEngine,
                    std::unique_ptr<synth::Oscillator> oscillator1,
                    std::unique_ptr<synth::Oscillator> oscillator2,
                    std::unique_ptr<synth::Filter> filter
            );

            static Synth &fromPtr(jlong pointer);

            void setStream(std::unique_ptr<synth::AudioStream> stream);

            synth::AudioStream &getStream();

            synth::Envelope &getAmpEnvelope();

            synth::AudioEngine &getAudioEngine();

            synth::Oscillator &getOscillator1();

            synth::Oscillator &getOscillator2();

            synth::Filter &getFilter();

        private:
            std::unique_ptr<synth::AudioStream> stream_;
            std::unique_ptr<synth::Envelope> ampEnvelope_;
            std::unique_ptr<synth::AudioEngine> audioEngine_;
            std::unique_ptr<synth::Oscillator> oscillator1_;
            std::unique_ptr<synth::Oscillator> oscillator2_;
            std::unique_ptr<synth::Filter> filter_;
        };
    }
}
#endif //JNI_COMPONENTS_H
