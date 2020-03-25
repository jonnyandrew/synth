#ifndef SYNTH_OSCILLATOR_H
#define SYNTH_OSCILLATOR_H

#include <vector>
#include "Waveform.h"

namespace synth {
    class Oscillator {
    public:
        Oscillator(
                const int sampleRate,
                std::unique_ptr<Waveform> waveform
        );

        void setPitch(const int pitch);

        void setPitchOffset(const int pitchOffset);

        int getPitchOffset();

        double getFrequency();

        void setWaveform(std::unique_ptr<Waveform> waveform);

        Waveform &getWaveform();

        void render(std::vector<float> &audioData, const int numFrames);

    private:
        int sampleRate_;
        int pitch_;
        int pitchOffset_;
        double frequency_;
        double phase_{0.0};
        std::unique_ptr<Waveform> waveform_;

        double calcFrequency(int pitch, int pitchOffset);
    };
}

#endif //SYNTH_OSCILLATOR_H
