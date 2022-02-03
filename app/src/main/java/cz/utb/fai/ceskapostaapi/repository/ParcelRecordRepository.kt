package cz.utb.fai.ceskapostaapi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cz.utb.fai.ceskapostaapi.database.ParcelRecordDatabase
import cz.utb.fai.ceskapostaapi.database.asModel
import cz.utb.fai.ceskapostaapi.models.ParcelRecord
import cz.utb.fai.ceskapostaapi.models.ParcelRecordState
import cz.utb.fai.ceskapostaapi.network.CPostApiProvider
import cz.utb.fai.ceskapostaapi.network.asDatabaseModel
import cz.utb.fai.ceskapostaapi.network.asModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParcelRecordRepository(private val database: ParcelRecordDatabase) {

    // History of searching
    val parcelRecords: LiveData<List<ParcelRecord>> = Transformations.map(database.parcelRecordDao.getParcelRecords()) {
        it.asModel()
    }

    suspend fun getParcelRecord(id: String = "DR4570705850U"): ParcelRecord? {
        var r: ParcelRecord?
        val dbResult = database.parcelRecordDao.getParcelRecord(id)
        r = dbResult?.asModel()
        if (r != null)
        {
            return r
        }
        else {
            withContext(Dispatchers.IO) {
                val parcelRecords = CPostApiProvider.parcelRecords.getParcelRecord(id)
                val dbParcelRecords = parcelRecords.body()!!.asDatabaseModel()
                for (parcelRecord in dbParcelRecords) {
                    database.parcelRecordDao.insertParcelRecord(parcelRecord.first, parcelRecord.second)
                }
                r = parcelRecords.body()!![0].asModel()
                //record = Transformations.map(parcelRecords[0])
            }
        }
        //val parcelRecords = CPostApiProvider.parcelRecords.getParcelRecord( "DR4570705850U")
        //val records = parcelRecords.body()
        Log.d("mev", id)
        return r

    }
    suspend fun getPseudoRecord(id: String): ParcelRecord {
        val state = ParcelRecordState(
            Id = id,
            Date = "b",
            Text = "c",
            PostCode = "d",
            PostOffice = "e"
        )

        val list: List<ParcelRecordState> = listOf(state);

        list
        return ParcelRecord(
        Id = id,
        ParcelType = "cd",
        Weight = 1.0,
        Cod = 0.0,
        StorageLength = 7,
        States = list
        )
    }
}