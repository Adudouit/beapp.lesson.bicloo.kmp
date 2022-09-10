package fr.beapp.lesson.bicloo.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.Marker
import fr.beapp.lesson.bicloo.core.rest.RestManager
import fr.beapp.lesson.bicloo.logic.StationEntity
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val manager = RestManager

    private val _stations = MutableLiveData<List<StationEntity>>()
    val stations: LiveData<List<StationEntity>>
        get() = _stations

    private val _selectedStation = MutableLiveData<StationEntity>()
    val selectedStation: LiveData<StationEntity>
        get() = _selectedStation

    private val markers = MutableLiveData<List<Marker>>()


    fun select(station: StationEntity) {
        _selectedStation.postValue(station)
    }

    fun loadAllStations(contractName:String) {
        viewModelScope.launch {
            runCatching {
                manager.getStationsOfCity(contractName)
            }
                .onSuccess { _stations.postValue(it) }
                .onFailure { Log.e(this::class.java.simpleName, "Failed to load stations from $contractName", it) }
        }
    }

    fun saveMarkers(markers: List<Marker>) = this.markers.postValue(markers)

    fun clearMarkers() {
        markers.value?.forEach { it.remove() }
        markers.postValue(emptyList())
    }
}