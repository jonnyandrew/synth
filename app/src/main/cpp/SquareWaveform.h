#ifndef SYNTH_SQUAREWAVEFORM_H
#define SYNTH_SQUAREWAVEFORM_H

#include "Waveform.h"

namespace synth {
    class SquareWaveform : public Waveform {
    public:
        float generate(double phase) override;
    };
}
#endif //SYNTH_SQUAREWAVEFORM_H
