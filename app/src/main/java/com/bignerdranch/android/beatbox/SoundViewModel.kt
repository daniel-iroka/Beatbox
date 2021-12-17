package com.bignerdranch.android.beatbox

// This file(Our MVVMs view model) is responsible for preparing the data our view will display
// which is a sound and a BeatBox to play that sound
// and this is what our adapter will directly interact with
class SoundViewModel {

    // TODO : When I come back, I will go to Binding to a view model.....
    var sound: Sound? = null
        set(sound) {
            field = sound
        }

    // THe title of the sound to be displayed in the buttons
    val title: String?
        get() = sound?.name
}