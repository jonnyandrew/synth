#ifndef SYNTH_ENVELOPECONTROLLEDAMPLIFIER_H
#define SYNTH_ENVELOPECONTROLLEDAMPLIFIER_H

#include "Envelope.h"
#include "SignalSource.h"

namespace synth {

    class EnvelopeControlledAmplifier : public SignalSource {

    public:
        EnvelopeControlledAmplifier(
                Envelope envelope
        );

        void startAttack();

        void startRelease();

        void getSignal(
                float *audioBuffer,
                const int numFrames
        );

    private:
        Envelope envelope_;
    };
}

#endif //SYNTH_ENVELOPECONTROLLEDAMPLIFIER_H
