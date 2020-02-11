#include "PlayException.h"

PlayException::PlayException(const std::string &string) : runtime_error(string) {}
