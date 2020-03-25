#include "../../synth/Waveform.h"
#include "../../synth/NoiseWaveform.h"
#include "../../synth/SineWaveform.h"
#include "../../synth/SquareWaveform.h"
#include "../../synth/TriangleWaveform.h"
#include "WaveformType.h"

auto jni::model::createWaveform(WaveformType type) -> std::unique_ptr <synth::Waveform> {
    std::unique_ptr <synth::Waveform> waveform;
    switch (type) {
        case WaveformType::Sine:
            waveform = std::make_unique<synth::SineWaveform>(type);
            break;
        case WaveformType::Triangle:
            waveform = std::make_unique<synth::TriangleWaveform>(type);
            break;
        case WaveformType::Square:
            waveform = std::make_unique<synth::SquareWaveform>(type);
            break;
        case WaveformType::Noise:
            waveform = std::make_unique<synth::NoiseWaveform>(type);
            break;
        default:
            throw std::invalid_argument(
                    "No such waveform for type " + std::to_string(type));
    }
    return std::move(waveform);

}
