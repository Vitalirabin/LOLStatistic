package com.example.lolstatistic.match.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lolstatistic.databinding.ItemMatchBinding
import com.example.lolstatistic.match.details.MatchModel

class MatchItemAdapter(
    val onClickListener: ItemOnClickListener
) : RecyclerView.Adapter<MatchItemAdapter.MyViewHolder>() {

    private var puuid = ""
    private var list = mutableListOf<MatchModel>()

    class MyViewHolder(binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        val kills: TextView = binding.matchCountOfKills
        val assists: TextView = binding.matchCountOfAssists
        val deaths: TextView = binding.countOfDeath
        val gameMode: TextView = binding.gameMode
        val status: TextView = binding.winLose
        val positionItem: TextView = binding.position
        val matchId: TextView = binding.matchId

        fun bind(
            match: MatchModel,
            onClickListener: ItemOnClickListener,
            puuid: String?
        ) {
            this.matchId.text = match.metadata.matchId.toString()
            this.positionItem.text = (position + 1).toString()
            this.kills.text =
                (match.info.participants?.firstOrNull { it.puuid == puuid }?.kills.toString())
            this.assists.text =
                (match.info.participants?.firstOrNull { it.puuid == puuid }?.assists.toString())
            this.deaths.text =
                (match.info.participants?.firstOrNull { it.puuid == puuid }?.deaths.toString())
            this.gameMode.text = match.info.gameMode
            if ((match.info.participants?.firstOrNull { it.puuid == puuid }?.win == true)
            ) {
                this.status.text = "Победа"
            } else this.status.text = "Поражение"
            this.itemView.setOnClickListener {
                onClickListener.onClick(match)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemMatchBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position], onClickListener, puuid)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    fun setData(puuid: String, list: List<MatchModel>) {
        this.list = list.toMutableList()
        this.puuid = puuid
        notifyDataSetChanged()
    }
}