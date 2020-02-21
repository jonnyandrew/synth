#ifndef SYNTH_ENVELOPE_H
#define SYNTH_ENVELOPE_H

#include "SignalSource.h"

namespace synth {
    class Envelope : public SignalSource {
    public:
        Envelope(
                int sampleRate,
                float attackTimeMs,
                float decayTimeMs,
                float sustainLevel,
                float releaseTimeMs
        );

        void startAttack();

        void startRelease();

        void getSignal(
                float *buffer,
                const int numFrames
        );
        float decayLevelIncrement_;
        float sustainStartTimeMs_;
    private:
        float attackTimeMs_;
        float sustainLevel_;
        double level_ = 0;
        float timeIncrement_;
        float time_;
        float attackLevelIncrement_;
        float releaseLevelIncrement_;
        /**
         * isTriggering is true during the attack, decay and sustain phases of the curve
         */
        bool isTriggering_ = false;
    };
}

#endif //SYNTH_ENVELOPE_H
