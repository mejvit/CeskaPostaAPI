package cz.utb.fai.ceskapostaapi.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.ceskapostaapi.database.getDatabase
import cz.utb.fai.ceskapostaapi.models.ParcelRecord
import cz.utb.fai.ceskapostaapi.repository.ParcelRecordRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    val selectedParcelId = MutableLiveData<String>()
    val parcelRecordId = MutableLiveData<String>()
    val parcelRecord = MutableLiveData<ParcelRecord>()
    private val parcelRecordsRepository = ParcelRecordRepository(getDatabase(application))

    init {
        parcelRecordId.value = ""
    }


    fun getRecord() {
        selectedParcelId.value = parcelRecordId.value
        //viewModelScope.launch {
        //    parcelRecord.value = parcelRecordsRepository.getParcelRecord(parcelRecordId.value.orEmpty())
        //}
    }
}