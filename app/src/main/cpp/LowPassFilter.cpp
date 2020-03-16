/*
Copyright 2012 Stefano D'Angelo <zanga.mail@gmail.com>

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THIS SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/

#include "LowPassFilter.h"
#include "Constants.h"

/*
This model is based on a reference implementation of an algorithm developed by
Stefano D'Angelo and Vesa Valimaki, presented in a paper published at ICASSP in 2013.
This improved model is based on a circuit analysis and compared against a reference
Ngspice simulation. In the paper, it is noted that this particular model is
more accurate in preserving the self-oscillating nature of the real filter.

References: "An Improved Virtual Analog Model of the Moog Ladder Filter"
Original Implementation: D'Angelo, Valimaki
*/

// Thermal voltage (26 milliwats at room temperature)
constexpr double VT = 0.312;

synth::LowPassFilter::LowPassFilter(const int sampleRate)
        : sampleRate_{static_cast<float>(sampleRate)},
          resonance_{0.1}, // NOLINT
          drive_{1.0F} // NOLINT
{
    setCutoff(1000.0F); // NOLINT
}

void synth::LowPassFilter::getSignal(std::vector<float> &buffer) {
    double dV0;
    double dV1;
    double dV2;
    double dV3;
    if(!isActive_) {
        return;
    }

    for (float &i : buffer) {
        dV0 = -g_ * (tanh((drive_ * i + resonance_ * V_[3]) / (2 * VT)) + tV_[0]);
        V_[0] += (dV0 + dV_[0]) / (2 * sampleRate_);
        dV_[0] = dV0;
        tV_[0] = tanh(V_[0] / (2 * VT));

        dV1 = g_ * (tV_[0] - tV_[1]);
        V_[1] += (dV1 + dV_[1]) / (2 * sampleRate_);
        dV_[1] = dV1;
        tV_[1] = tanh(V_[1] / (2 * VT));

        dV2 = g_ * (tV_[1] - tV_[2]);
        V_[2] += (dV2 + dV_[2]) / (2 * sampleRate_);
        dV_[2] = dV2;
        tV_[2] = tanh(V_[2] / (2 * VT));

        dV3 = g_ * (tV_[2] - tV_[3]);
        V_[3] += (dV3 + dV_[3]) / (2 * sampleRate_);
        dV_[3] = dV3;
        tV_[3] = tanh(V_[3] / (2 * VT));

        i = static_cast<float>(V_[3]);
    }
}

void synth::LowPassFilter::setResonance(float resonance) {
    assert(resonance <= MAX_RESONANCE);
    assert(resonance >= 0.0F);
    resonance_ = resonance;
}

void synth::LowPassFilter::setCutoff(float cutoff) {
    double x = (PI * cutoff) / sampleRate_;
    g_ = 4.0 * PI * VT * cutoff * (1.0 - x) / (1.0 + x); //NOLINT
}

void synth::LowPassFilter::setIsActive(bool isActive) {
    isActive_ = isActive;
}
