package fr.beapp.lesson.bicloo.ui.station

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.beapp.lesson.bicloo.R
import fr.beapp.lesson.bicloo.core.rest.StationDTO
import fr.beapp.lesson.bicloo.databinding.StationItemBinding
import fr.beapp.lesson.shared.logic.StationEntity

class StationItem(private val binding: StationItemBinding, private val onStationClicked: (StationEntity) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(station: StationEntity) {
        val context = binding.root.context
        binding.stationItemName.text = station.name
        binding.stationItemAddress.text = station.address
        val openStateColor = ContextCompat.getColor(context, if (station.state == StationDTO.State.OPEN) R.color.green else R.color.red)
        binding.stationItemOpenState.setBackgroundColor(openStateColor)
        binding.root.setOnClickListener { onStationClicked(station) }
    }
}