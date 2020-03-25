#ifndef SYNTH_SQUAREWAVEFORM_H
#define SYNTH_SQUAREWAVEFORM_H

#include "Waveform.h"

namespace synth {
    class SquareWaveform : public Waveform {
    public:
        SquareWaveform(int label = -1);

        float generate(double phase) override;
    };
}
#endif //SYNTH_SQUAREWAVEFORM_H
