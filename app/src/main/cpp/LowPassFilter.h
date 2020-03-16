#ifndef SYNTH_LOWPASSFILTER_H
#define SYNTH_LOWPASSFILTER_H

#include "SignalSource.h"
#include <vector>

namespace synth {

    class LowPassFilter : public SignalSource {
    public:
        LowPassFilter(const int sampleRate);

        void getSignal(
                std::vector<float> &buffer
        ) override;


        void setCutoff(float cutoff);

        // [0, 4]
        void setResonance(float resonance);

    private:
        float sampleRate_;
        float resonance_;

        double V_[4]{};
        double dV_[4]{};
        double tV_[4]{};

        double g_{};
        double drive_;
    };

}

#endif //SYNTH_LOWPASSFILTER_H
