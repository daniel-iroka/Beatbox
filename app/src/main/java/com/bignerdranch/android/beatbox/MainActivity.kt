package com.bignerdranch.android.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.beatbox.databinding.ActivityMainBinding
import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {
    private lateinit var beatBox : BeatBox
    private lateinit var beatBoxViewModel: BeatBoxViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox = BeatBox(assets)

        // Todo : When I come back, I will go through XML drawables again and go to : For the More Curious: 9-Patch Images

        // Using the FactoryModel technique...
        val factoryModel =  BeatBoxFactoryModel(assets)
        beatBoxViewModel = ViewModelProvider(this, factoryModel).get(BeatBoxViewModel::class.java)


        // This is to create(inflate) an Instance of our ActivityMainBinding binding class
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context ,3)
            adapter = SoundAdapter(beatBox.sounds)
        }

        val volumeSeekbar = findViewById<SeekBar>(R.id.volume_seekBar)
        val playBackSpeed = findViewById<TextView>(R.id.playback_textView)
        volumeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val p1 = progress.toString()
                val p2 = progress / 100f  // this reduces the speed of the sound
                val beatBox = beatBox
                val percentageSign = getString(R.string.percentage)
                beatBox.rate = p2
                playBackSpeed.text = getString(R.string.seekBar_textView2, p1, percentageSign)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // code for when progress is started
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // code for when progress is stopped
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release() // Same release function to drop sound after playing
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding):
            RecyclerView.ViewHolder(binding.root)  {

                init {
                    binding.viewModel = SoundViewModel(beatBox)
                }

                // Hooking up our views, our view model(binding data) with our recyclerView
                fun bind(sound: Sound) {
                    binding.apply {
                        viewModel?.sound = sound
                        executePendingBindings()
                    // this function cancels any delay in our layout rendering views because we are passing in binding data
                    }
                }
            }


    // Sound Adapter to display our RecyclerView Views through our ViewHolder(SoundHolder)
    private inner class SoundAdapter(private val sounds: List<Sound>):
        RecyclerView.Adapter<SoundHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater, R.layout.list_item_sound, parent, false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position] // Implementing our onBindViewHolder
            holder.bind(sound)
        }
        override fun getItemCount() = sounds.size

    }
}
