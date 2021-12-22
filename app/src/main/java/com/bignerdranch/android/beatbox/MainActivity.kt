package com.bignerdranch.android.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.beatbox.databinding.ActivityMainBinding
import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {
    private lateinit var beatBox: BeatBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox = BeatBox(assets)

        // This is to create(inflate) an Instance of our ActivityMainBinding binding class
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context ,3)
            adapter = SoundAdapter(beatBox.sounds)
        }
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
