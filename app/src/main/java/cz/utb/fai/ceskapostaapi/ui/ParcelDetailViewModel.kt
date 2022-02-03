package cz.utb.fai.ceskapostaapi.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import cz.utb.fai.ceskapostaapi.models.ParcelRecord
import cz.utb.fai.ceskapostaapi.repository.ParcelRecordRepository
import cz.utb.fai.ceskapostaapi.database.getDatabase
import cz.utb.fai.ceskapostaapi.models.ParcelRecordState
import kotlinx.coroutines.launch

class ParcelDetailViewModel(parcelRecordId: String, application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    val parcelRecord = MutableLiveData<ParcelRecord>()
    val receivedId = MutableLiveData<String>()
    val states = MutableLiveData<List<ParcelRecordState>>()
    private val parcelRecordsRepository = ParcelRecordRepository(getDatabase(application))

    init {
        viewModelScope.launch {
            receivedId.value = parcelRecordId
            parcelRecord.value = parcelRecordsRepository.getParcelRecord(parcelRecordId)
            states.value = parcelRecord.value?.States
        }
    }

    private fun loadParcelRecord(id: String) {
        viewModelScope.launch {
            parcelRecord.value = parcelRecordsRepository.getPseudoRecord(id)
        }
    }
}