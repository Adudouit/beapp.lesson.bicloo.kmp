package fr.beapp.lesson.bicloo.ui.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import fr.beapp.lesson.bicloo.databinding.StationsFragmentBinding
import fr.beapp.lesson.bicloo.logic.StationEntity
import fr.beapp.lesson.bicloo.ui.home.HomeViewModel
import fr.beapp.lesson.bicloo.ui.map.MapFragment

class StationsFragment : Fragment() {

    private val adapter = StationsAdapter(this::onStationClicked)
    private val viewModel: HomeViewModel by activityViewModels()

    private var _binding: StationsFragmentBinding? = null
    private val binding: StationsFragmentBinding
        get() = _binding ?: throw NullPointerException("${MapFragment::class.java.simpleName}: should not attempt to access binding when view is destroy or not initialized")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = StationsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.stationsFragmentRecyclerView.adapter = adapter
        viewModel.stations.observe(viewLifecycleOwner, adapter::replaceAll)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun onStationClicked(station: StationEntity) {
        viewModel.select(station)
    }
}