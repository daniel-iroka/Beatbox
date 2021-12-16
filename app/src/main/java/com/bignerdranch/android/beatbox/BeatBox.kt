package com.bignerdranch.android.beatbox

import android.content.res.AssetManager
import android.util.Log

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"

// TODO : WHEN I COME BACK, I WILL GO TO THROUGH MVVM AND DATA BINDING
// TODO : THEN I WILL GO TO WIRING UP ASSETS...
// This file is responsible for managing our sound assets. This includes finding, keeping track of them and playing the sounds
class BeatBox(private val assets: AssetManager) {

    fun loadSounds(): List<String> {
        return try {
            val soundNames = assets.list(SOUNDS_FOLDER)!!
            Log.d(TAG, "Found ${soundNames.size} sounds")
            soundNames.asList()
        } catch (e:Exception) {
            Log.e(TAG, "Could not list assets", e)
            emptyList()
        }
    }
}