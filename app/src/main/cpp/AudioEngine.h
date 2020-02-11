//
// Created by jonny on 10/02/20.
//

#ifndef SYNTH_AUDIOENGINE_H
#define SYNTH_AUDIOENGINE_H


#include <atomic>
#include "Oscillator.h"
#include "PlayException.h"

class AudioEngine : oboe::AudioStreamCallback {

public:
    AudioEngine(
            Oscillator &oscillator
    );

    oboe::DataCallbackResult onAudioReady(
            oboe::AudioStream *oboeStream,
            void *audioData,
            int32_t numFrames);

    void start();
    void playNote();
    void stopNote();

private:
    Oscillator *oscillator_;
    oboe::ManagedStream stream_;
};


#endif //SYNTH_AUDIOENGINE_H
