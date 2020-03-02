#ifndef SYNTH_ENVELOPE_H
#define SYNTH_ENVELOPE_H

#include "SignalSource.h"
#include "EnvelopeParameters.h"

namespace synth {
    class Envelope : public SignalSource {
    public:
        Envelope(
                int sampleRate,
                EnvelopeParameters envelopeParameters
        );

        void startAttack();

        void startRelease();

        void getSignal(
                float *buffer,
                const int numFrames
        );

        void setEnvelopeParameters(EnvelopeParameters envelopeParameters);

        EnvelopeParameters getEnvelopeParameters();

    private:
        EnvelopeParameters params_;
        double level_{0};
        float timeIncrement_;
        float time_{0};
        float sustainStartTimeMs_;
        float attackLevelIncrement_;
        float decayLevelIncrement_;
        float releaseLevelIncrement_;
        /**
         * isTriggering is true during the attack, decay and sustain phases of the curve
         */
        bool isTriggering_{false};

        float calcSustainStartTimeMs_(float attackTimeMs, float decayTimeMs);
        float calcAttackLevelIncrement_(float attackTimeMs, float timeIncrement);
        float calcDecayLevelIncrement_(float sustainLevel, float decayTimeMs, float timeIncrement);
        float calcReleaseLevelIncrement_(float sustainLevel, float releaseTimeMs, float timeIncrement);
    };
}

#endif //SYNTH_ENVELOPE_H
