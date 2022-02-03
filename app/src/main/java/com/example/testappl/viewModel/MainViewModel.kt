package com.example.testappl.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappl.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val result = repository.result

    fun checkURL(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkURL(url)
        }
    }
}