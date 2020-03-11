#ifndef SYNTH_WAVEFORM_H
#define SYNTH_WAVEFORM_H

namespace synth {
    class Waveform {

    public:
        virtual ~Waveform() = default;

        virtual float generate(const double phase) = 0;
    };
}

#endif //SYNTH_WAVEFORM_H
