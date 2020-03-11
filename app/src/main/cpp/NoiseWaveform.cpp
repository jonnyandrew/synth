#include "NoiseWaveform.h"

#include <random>

auto synth::NoiseWaveform::generate(
        const double /*phase*/
) -> float {
    return static_cast<float>(
                   std::rand() // NOLINT(cert-msc30-c, cert-msc50-cpp)
           ) /
           static_cast<float>(RAND_MAX);
}
