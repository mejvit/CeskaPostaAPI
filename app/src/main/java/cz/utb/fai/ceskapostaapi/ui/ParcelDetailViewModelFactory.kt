package cz.utb.fai.ceskapostaapi.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ParcelDetailViewModelFactory (
    private val parcelRecordId: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParcelDetailViewModel::class.java)) {
            return ParcelDetailViewModel(parcelRecordId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}