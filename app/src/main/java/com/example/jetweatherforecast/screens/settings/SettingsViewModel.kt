package com.example.jetweatherforecast.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.model.Unit
import com.example.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {

    private val _unitsList = MutableStateFlow<List<Unit>>(emptyList())
    val unitsList = _unitsList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits()
                .distinctUntilChanged()
                .collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {
                        // ERROR
                    } else {
                        _unitsList.value = listOfUnits
                    }
                }
        }
    }

    fun insertUnit(unit: Unit) =
        viewModelScope.launch { repository.insertUnit(unit) }

    fun updateUnit(unit: Unit) =
        viewModelScope.launch { repository.updateUnit(unit) }

    fun deleteAllUnits() =
        viewModelScope.launch { repository.deleteAllUnits() }

    fun deleteUnit(unit: Unit) =
        viewModelScope.launch { repository.deleteUnit(unit) }

}