#ifndef SYNTH_AUDIOENGINE_H
#define SYNTH_AUDIOENGINE_H

#include "Oscillator.h"
#include "PlayException.h"
#include "SignalSource.h"
#include "AudioStream.h"
#include "EnvelopeControlledAmplifier.h"

namespace synth {
    class AudioEngine : public SignalSource {

    public:
        AudioEngine(
                Oscillator oscillator1,
                Oscillator oscillator2,
                EnvelopeControlledAmplifier envelopeControlledAmplifier
        );

        void playNote(const int32_t pitch);

        void stopNote();

        void getSignal(
                float *audioBuffer,
                const int numFrames
        );

    private:
        Oscillator oscillator1_;
        Oscillator oscillator2_;
        EnvelopeControlledAmplifier envelopeControlledAmplifier_;
    };
}

#endif //SYNTH_AUDIOENGINE_H
