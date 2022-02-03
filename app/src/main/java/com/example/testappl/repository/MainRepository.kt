package com.example.testappl.repository

import android.content.Context
import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import com.example.testappl.repository.interfaces.IMainRepository
import javax.inject.Inject

class MainRepository @Inject constructor(val context: Context) : IMainRepository {
    val result = MutableLiveData<Boolean>()

    override fun checkURL(url: String) {
        result.postValue(URLUtil.isValidUrl(url))
    }
}