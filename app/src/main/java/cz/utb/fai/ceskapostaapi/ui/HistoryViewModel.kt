package cz.utb.fai.ceskapostaapi.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import cz.utb.fai.ceskapostaapi.database.getDatabase
import cz.utb.fai.ceskapostaapi.repository.ParcelRecordRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val parcelRecordsRepository = ParcelRecordRepository(getDatabase(application))
    var records = parcelRecordsRepository.parcelRecords
    // TODO: Implement the ViewModel

    init {
        records = parcelRecordsRepository.parcelRecords
    }
}