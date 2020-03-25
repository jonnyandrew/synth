#ifndef SYNTH_SINEWAVEFORM_H
#define SYNTH_SINEWAVEFORM_H

#include "Waveform.h"

namespace synth {
    class SineWaveform : public Waveform {
    public:
        SineWaveform(int label = -1);

        float generate(const double phase) override;
    };
}

#endif //SYNTH_SINEWAVEFORM_H
