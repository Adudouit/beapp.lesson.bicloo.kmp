package fr.beapp.lesson.bicloo.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import fr.beapp.lesson.bicloo.databinding.MapFragmentBinding
import fr.beapp.lesson.shared.logic.StationEntity
import fr.beapp.lesson.bicloo.ui.home.HomeViewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: MapFragmentBinding? = null
    private val binding: MapFragmentBinding
        get() = _binding ?: throw NullPointerException("${MapFragment::class.java.simpleName}: should not attempt to access binding when view is destroy or not initialized")

    private val viewModel: HomeViewModel by activityViewModels()
    private var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MapFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // No need to catch the ClassCastException
        // If the map fragment is not found, the app may as well crash, since it's caused by a development mistake
        binding.googleMap.getFragment<SupportMapFragment>().getMapAsync(this)
    }

    override fun onDestroyView() {
        viewModel.clearMarkers()
        googleMap = null
        _binding = null
        super.onDestroyView()
    }

    override fun onMapReady(gMap: GoogleMap) {
        this.googleMap = gMap
        viewModel.stations.observe(viewLifecycleOwner, this::onStationsLoaded)
        viewModel.selectedStation.observe(viewLifecycleOwner, this::onStationSelected)
    }

    private fun onStationsLoaded(stations: List<StationEntity>) {
        // generate markers
        val builder = LatLngBounds.Builder()
        viewModel.clearMarkers()
        val markers = stations.mapNotNull {
            val marker = addMarker(it)
            if (marker != null) builder.include(marker.position)
            marker
        }
        viewModel.saveMarkers(markers)

        // move camera to fit all markers
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 120))
    }

    private fun addMarker(station: StationEntity): Marker? {
        val latLng = LatLng(station.latitude.toDouble(), station.longitude.toDouble())

        return googleMap?.addMarker(MarkerOptions().position(latLng).title(station.name))
    }

    private fun onStationSelected(station: StationEntity) {
        val latLng = LatLng(station.latitude.toDouble(), station.longitude.toDouble())
        val cameraPosition = CameraPosition.Builder().target(latLng).zoom(18F).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        googleMap?.animateCamera(cameraUpdate)
    }
}