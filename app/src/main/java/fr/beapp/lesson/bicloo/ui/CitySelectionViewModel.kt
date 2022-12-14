package fr.beapp.lesson.bicloo.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.beapp.lesson.shared.core.cache.Storage
import fr.beapp.lesson.shared.logic.ContractEntity
import fr.beapp.lesson.shared.core.rest.RestDataSource
import kotlinx.coroutines.launch

class CitySelectionViewModel : ViewModel() {

	private val manager = RestDataSource

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
                manager.getContracts(fromCache = true)
            }
                .onSuccess { _contracts.postValue(it) }
                .onFailure { Log.e(this::class.java.simpleName, "Failed to load contracts, cause: ", it) }
        }
    }

    fun searchForContract(city: String) {
        val contract = _contracts.value?.find { it.match(city) }
//        println("[SEARCH] found $contract")
        _contractNameFound.value = when (contract) {
            null -> Result.failure(NoContractFoundException(city))
            else -> Result.success(contract.name)
        }
    }


    class NoContractFoundException(city: String) : Exception("No contract found for city: $city")
}