#ifndef SYNTH_NOISEWAVEFORM_H
#define SYNTH_NOISEWAVEFORM_H

#include "Waveform.h"

namespace synth {
    class NoiseWaveform : public Waveform {
    public:
        NoiseWaveform(int label);

        float generate(const double phase) override;
    };
}

#endif //SYNTH_NOISEWAVEFORM_H
