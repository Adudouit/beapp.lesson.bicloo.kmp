package fr.beapp.lesson.bicloo.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.beapp.lesson.bicloo.core.rest.RestManager
import fr.beapp.lesson.bicloo.logic.ContractEntity
import kotlinx.coroutines.launch

class CitySelectionViewModel : ViewModel() {

    private val manager = RestManager

    private val _contracts = MutableLiveData<List<ContractEntity>>(emptyList())
    private val _contractNameFound = MutableLiveData<Result<String>>()
    val contractNameFound: LiveData<Result<String>>
        get() = _contractNameFound

    init {
        loadContracts()
    }

    private fun loadContracts() {
        viewModelScope.launch {
            runCatching {
                manager.getContracts()
            }
                .onSuccess { _contracts.postValue(it) }
                .onFailure { Log.e(this::class.java.simpleName, "Failed to load contracts, cause: ", it) }
        }
    }

    fun searchForContract(city: String) {
        val contract = _contracts.value?.find { it.name == city || it.cities?.contains(city) == true }
        _contractNameFound.value = when (contract) {
            null -> Result.failure(NoContractFoundException(city))
            else -> Result.success(contract.name)
        }
    }


    class NoContractFoundException(city: String) : Exception("No contract found for city: $city")
}