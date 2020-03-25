#ifndef SYNTH_WAVEFORMTYPE_H
#define SYNTH_WAVEFORMTYPE_H

#include <memory>

namespace jni {
    namespace model {
        enum WaveformType {
            Sine,
            Square,
            Triangle,
            Noise
        };

        std::unique_ptr<synth::Waveform> createWaveform(WaveformType type);
    }
}

#endif //SYNTH_WAVEFORMTYPE_H
