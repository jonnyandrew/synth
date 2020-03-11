#ifndef SYNTH_TRIANGLEWAVEFORM_H
#define SYNTH_TRIANGLEWAVEFORM_H

#include "Waveform.h"

namespace synth {
    class TriangleWaveform : public Waveform {
    public:
        virtual float generate(const double phase) override;
    };
}

#endif //SYNTH_TRIANGLEWAVEFORM_H
