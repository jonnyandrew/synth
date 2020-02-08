package com.flatmapdev.synth.engineData

class SynthEngine {
    external fun getVersion(): String

    companion object {

        init {
            System.loadLibrary("synth-engine")
        }
    }
}