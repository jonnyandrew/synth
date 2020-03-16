#ifndef SYNTH_AUDIOENGINE_H
#define SYNTH_AUDIOENGINE_H

#include "Oscillator.h"
#include "PlayException.h"
#include "SignalSource.h"
#include "AudioStream.h"
#include "EnvelopeControlledAmplifier.h"
#include "LowPassFilter.h"

namespace synth {
    class AudioEngine : public SignalSource {

    public:
        AudioEngine(
                Oscillator &oscillator1,
                Oscillator &oscillator2,
                EnvelopeControlledAmplifier envelopeControlledAmplifier,
                LowPassFilter &lowPassFilter
        );

        void playNote(const int32_t pitch);

        void stopNote();

        void getSignal(
                std::vector<float> &buffer
        ) override;

    private:
        Oscillator *oscillator1_;
        Oscillator *oscillator2_;
        EnvelopeControlledAmplifier envelopeControlledAmplifier_;
        LowPassFilter *lowPassFilter_;
    };
}

#endif //SYNTH_AUDIOENGINE_H
