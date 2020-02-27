#ifndef SYNTH_AUDIOSTREAM_H
#define SYNTH_AUDIOSTREAM_H

#include <oboe/AudioStream.h>
#include <oboe/Oboe.h>
#include "SignalSource.h"

namespace synth {
    class AudioStream : oboe::AudioStreamCallback {
    public:
        AudioStream(SignalSource &audioSource);

        void close();

        static int getSampleRate();

        oboe::DataCallbackResult onAudioReady(
                oboe::AudioStream *oboeStream,
                void *audioData,
                int32_t numFrames);

    private:
        oboe::ManagedStream oboeStream_;

        SignalSource *audioSource_;

        static int sampleRate_;
    };
};
#endif //SYNTH_AUDIOSTREAM_H
