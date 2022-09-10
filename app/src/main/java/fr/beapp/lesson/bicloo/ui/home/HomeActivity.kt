package fr.beapp.lesson.bicloo.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.beapp.lesson.bicloo.R
import fr.beapp.lesson.bicloo.databinding.HomeActivityBinding
import fr.beapp.lesson.bicloo.logic.StationEntity
import fr.beapp.lesson.bicloo.ui.map.MapFragment
import fr.beapp.lesson.bicloo.ui.station.StationsFragment

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_CONTRACT_NAME = "EXTRA_CONTRACT_NAME"
        fun intent(context: Context, contract: String) = Intent(context, HomeActivity::class.java)
            .putExtra(EXTRA_CONTRACT_NAME, contract)
    }

    private lateinit var binding: HomeActivityBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val contractName: String by lazy { intent.getStringExtra(EXTRA_CONTRACT_NAME) ?: throw Exception("Requires a contract name") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareFragments()
        viewModel.selectedStation.observe(this, this::onStationSelected)
        viewModel.loadAllStations(contractName)
    }

    private fun prepareFragments() {
        val fragments = listOf(
            StationsFragment(),
            MapFragment()
        )
        binding.mainActivityViewPager.adapter = MainAdapter(this, fragments)
        binding.mainActivityViewPager.isUserInputEnabled = false
        binding.mainActivityTabBar.setOnItemSelectedListener {
            val index = when (it.itemId) {
                R.id.mainActivity_menu_stations -> 0
                R.id.mainActivity_menu_map -> 1
                else -> -1
            }
            if (index >= 0) binding.mainActivityViewPager.currentItem = index
            true
        }
    }

    private fun onStationSelected(station: StationEntity) {
        binding.mainActivityTabBar.selectedItemId = R.id.mainActivity_menu_map
    }

    private class MainAdapter(activity: FragmentActivity, private val fragments: List<Fragment>) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = fragments[position]

    }
}