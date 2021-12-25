package com.bignerdranch.android.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5


// This file is responsible for managing our sound assets. This includes finding, keeping track of them and playing the sounds
// This file is basically us getting the sounds...
class BeatBox(private val assets: AssetManager) {
    val sounds: List<Sound>
    private val soundPool = SoundPool.Builder() // creates a SoundPool instance
        .setMaxStreams(MAX_SOUNDS) // specifies the amount of sounds that can play at a time
        .build()

    init {
        sounds = loadSounds()
    }

    var rate = 1.0f

    // this function to play sounds
    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, rate)
        }
    }

    // Release function to drop a sound after its playing
    fun release() {
        soundPool.release()
    }

    private fun loadSounds(): List<Sound> {
        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e:Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            // Handling the exception for loading sounds in our SoundPool
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "Could not load sound $filename", ioe)
            }
        }
        return sounds
    }

    // This function will load sounds into our SoundPool
    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}
