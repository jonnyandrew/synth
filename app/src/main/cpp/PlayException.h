#ifndef SYNTH_PLAYEXCEPTION_H
#define SYNTH_PLAYEXCEPTION_H


#include <exception>
#include <string>

class PlayException : std::runtime_error {
public:
    PlayException(const std::string &string);
};


#endif //SYNTH_PLAYEXCEPTION_H
