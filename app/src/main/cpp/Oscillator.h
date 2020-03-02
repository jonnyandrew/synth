#ifndef SYNTH_OSCILLATOR_H
#define SYNTH_OSCILLATOR_H

#include <vector>

namespace synth {
    class Oscillator {
    public:
        Oscillator(const int sampleRate);

        void setPitch(const int pitch);

        void render(std::vector<float> &audioData, const int numFrames);

    private:
        double phase_ = 0.0;
        double phaseIncrement_ = 0.0;
        int sampleRate_;
    };
}

#endif //SYNTH_OSCILLATOR_H
