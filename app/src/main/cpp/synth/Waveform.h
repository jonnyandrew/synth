#ifndef SYNTH_WAVEFORM_H
#define SYNTH_WAVEFORM_H

#include <string>

namespace synth {
    class Waveform {

    public:
        Waveform(int label);

        virtual ~Waveform() = default;

        virtual float generate(const double phase) = 0;

        int getLabel();

    private:
        int label{};
    };
}

#endif //SYNTH_WAVEFORM_H
