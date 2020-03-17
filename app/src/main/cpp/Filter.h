#ifndef SYNTH_FILTER_H
#define SYNTH_FILTER_H

#include "SignalSource.h"
#include <VAStateVariableFilter.h>
#include <vector>

namespace synth {

    class Filter : public SignalSource {
    public:
        Filter(const int sampleRate);

        void getSignal(
                std::vector<float> &buffer
        ) override;

        void setIsActive(bool isActive);

        void setCutoff(float cutoff);

        float getCutoff();

        // [0, 1]
        void setResonance(float resonance);

        float getResonance();

    private:
        VAStateVariableFilter vaStateVariableFilter_{};
        float sampleRate_;
        bool isActive_{true};
        float resonance_;
    };

}

#endif //SYNTH_FILTER_H
