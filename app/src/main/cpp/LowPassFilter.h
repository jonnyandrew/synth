#ifndef SYNTH_LOWPASSFILTER_H
#define SYNTH_LOWPASSFILTER_H

#include "SignalSource.h"
#include <vector>

namespace synth {

    class LowPassFilter : public SignalSource {
    public:
        static constexpr float MAX_RESONANCE = 4;

        LowPassFilter(const int sampleRate);

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
        float sampleRate_;
        bool isActive_{true};
        float resonance_;
        float cutoff_{};

        double V_[4]{};
        double dV_[4]{};
        double tV_[4]{};

        double g_{};
        double drive_;
    };

}

#endif //SYNTH_LOWPASSFILTER_H
