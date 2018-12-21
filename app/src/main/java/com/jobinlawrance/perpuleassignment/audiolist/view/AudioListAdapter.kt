package com.jobinlawrance.perpuleassignment.audiolist.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jobinlawrance.perpuleassignment.R
import com.jobinlawrance.perpuleassignment.audiolist.entities.AudioData
import com.jobinlawrance.perpuleassignment.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_audiolist.*

class AudioListAdapter(private val clickListener: (itemId: String) -> Unit) :
    RecyclerView.Adapter<AudioListAdapter.AudioListViewHolder>() {

    var audioList: List<AudioData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioListViewHolder {
        val view = parent.inflate(R.layout.row_audiolist)
        return AudioListViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int = audioList.size

    override fun onBindViewHolder(holder: AudioListViewHolder, position: Int) = holder.bindView(audioList[position])

    fun addAudioList(audioList: List<AudioData>) {
        this.audioList = audioList
        notifyDataSetChanged()
    }

    class AudioListViewHolder(override val containerView: View, private val clickListener: (itemId: String) -> Unit) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindView(audioData: AudioData) {
            audio_name.text = audioData.desc
            containerView.setOnClickListener { clickListener.invoke(audioData.itemId) }
        }
    }
}