#ifndef SYNTH_SIGNALSOURCE_H
#define SYNTH_SIGNALSOURCE_H

#include <vector>

namespace synth {
    class SignalSource {
    public:
        virtual ~SignalSource() = default;

        virtual void getSignal(
                std::vector<float> &buffer
        ) = 0;
    };
}

#endif //SYNTH_SIGNALSOURCE_H
