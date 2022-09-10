package fr.beapp.lesson.bicloo.ui.station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.beapp.lesson.bicloo.databinding.StationItemBinding
import fr.beapp.lesson.bicloo.logic.StationEntity

class StationsAdapter(private val onStationClicked: (StationEntity) -> Unit) : RecyclerView.Adapter<StationItem>() {

    private val stations = mutableListOf<StationEntity>()

    fun replaceAll(stations: List<StationEntity>) {
        this.stations.clear()
        this.stations.addAll(stations)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StationItem(StationItemBinding.inflate(layoutInflater, parent, false), onStationClicked)
    }

    override fun onBindViewHolder(holder: StationItem, position: Int) = holder.bind(stations[position])

    override fun getItemCount(): Int = stations.size
}