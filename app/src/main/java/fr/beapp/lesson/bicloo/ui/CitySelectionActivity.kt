package fr.beapp.lesson.bicloo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.beapp.lesson.bicloo.databinding.CitySelectionActivityBinding
import fr.beapp.lesson.bicloo.ui.home.HomeActivity
import fr.beapp.lesson.shared.Greeting

class CitySelectionActivity : AppCompatActivity() {

    private lateinit var binding: CitySelectionActivityBinding
    private val viewModel: CitySelectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set view
        binding = CitySelectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.citySelectionActivitySubmit.setOnClickListener {
            val city = binding.citySelectionActivitySearch.editText?.text?.toString()?.trim() ?: return@setOnClickListener
            viewModel.searchForContract(city)
        }
        viewModel.contractNameFound.observe(this, this::onSearchResult)



        val greeting = Greeting().greeting()
        println("[KMP] $greeting")
    }


    private fun onSearchResult(result: Result<String>) {
        when {
            result.isFailure -> Snackbar.make(binding.root, "${result.exceptionOrNull()?.message}", Snackbar.LENGTH_SHORT).show()
            result.isSuccess -> result.getOrNull()?.let(this::navigateToHomeActivity)
        }
    }


    private fun navigateToHomeActivity(city: String) {
        startActivity(HomeActivity.intent(this, city))
    }
}