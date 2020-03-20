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
                int32_t numFrames) override;

        void onErrorBeforeClose(oboe::AudioStream *oboeStream, oboe::Result error) override;

        void onErrorAfterClose(oboe::AudioStream *oboeStream, oboe::Result error) override;

    private:
        oboe::ManagedStream oboeStream_;

        SignalSource *audioSource_;

        static int sampleRate_;

        void initNewStream();
    };
};
#endif //SYNTH_AUDIOSTREAM_H
