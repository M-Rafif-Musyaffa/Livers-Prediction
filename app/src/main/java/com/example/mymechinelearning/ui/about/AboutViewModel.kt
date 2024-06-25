package com.example.mymechinelearning.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val teksJudul = MutableLiveData<String>().apply {

    }
    val textjudul: LiveData<String> = teksJudul

    private val teksNama = MutableLiveData<String>().apply {

    }
    val textnama: LiveData<String> = teksNama

    private val teksdecs = MutableLiveData<String>().apply {

    }
    val textdeksripsi: LiveData<String> = teksdecs
}