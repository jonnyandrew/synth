#ifndef SYNTH_ENVELOPEPARAMETERS_H
#define SYNTH_ENVELOPEPARAMETERS_H

namespace synth {
    struct EnvelopeParameters {
        float attackTimeMs;
        float decayTimeMs;
        float sustainLevel;
        float releaseTimeMs;
    };
}

#endif //SYNTH_ENVELOPEPARAMETERS_H
