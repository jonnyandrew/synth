#include "PlayException.h"

synth::PlayException::PlayException(const std::string &string) : runtime_error(string) {}
