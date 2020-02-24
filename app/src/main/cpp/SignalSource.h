#ifndef SYNTH_SIGNALSOURCE_H
#define SYNTH_SIGNALSOURCE_H

namespace synth {
    class SignalSource {
    public:
        virtual ~SignalSource() = default;

        virtual void getSignal(
                float *buffer,
                const int numFrames
        ) = 0;
    };
}

#endif //SYNTH_SIGNALSOURCE_H
