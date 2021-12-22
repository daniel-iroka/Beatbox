package com.bignerdranch.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

// This file(Our MVVMs view model) is responsible for preparing the data our view will display which is a sound and a BeatBox to play that sound
// and this is what our adapter will directly interact with

class SoundViewModel(private val beatBox : BeatBox): BaseObservable() {
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

    // TODO : When I come back, I will review from Test Dependencies and move on....

    /**
     *  Note: BaseObservable works like LiveData which are both used to observe changes in data
     * **/
    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        // this notifies our bindingClass that our property has been set and should then update the views
        }

    // The title of the sound to be displayed in the buttons
    @get:Bindable
    val title: String?
        get() = sound?.name
}