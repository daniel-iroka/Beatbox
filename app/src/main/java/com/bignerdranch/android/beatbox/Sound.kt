package com.bignerdranch.android.beatbox

private const val WAV = ".wav"

// This file is responsible for handling information as regards to the sound being currently displayed to the User
// like keeping track of the fileName of the sound
class Sound(val assetPath: String, var soundId: Int? = null) {

    val name = assetPath.split("/").last().removeSuffix(WAV)
}