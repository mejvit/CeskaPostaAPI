package cz.utb.fai.ceskapostaapi.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParcelRecordDao {
    @Transaction
    @Query("SELECT * FROM ParcelRecordEntity")
    fun getParcelRecords(): LiveData<List<ParcelRecordWithStates>>

    @Query("SELECT * FROM ParcelRecordEntity WHERE Id LIKE :Id LIMIT 1")
    suspend fun getParcelRecord(Id: String): ParcelRecordWithStates?

    @Transaction
    fun insertParcelRecord(parcel: ParcelRecordEntity, states: List<ParcelRecordStateEntity>) {
        val parcelId = insertParcelRecord(parcel)

        for (state in states) {
            state.parcelRecordId = parcelId
            insertParcelRecord(state)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertParcelRecord(parcel: ParcelRecordEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertParcelRecord(state: ParcelRecordStateEntity)
}

@Database(entities = [ParcelRecordEntity::class, ParcelRecordStateEntity::class], version = 1)
abstract class ParcelRecordDatabase: RoomDatabase() {
    abstract val parcelRecordDao: ParcelRecordDao
}

private lateinit var INSTANCE: ParcelRecordDatabase

fun getDatabase(context: Context): ParcelRecordDatabase {
    synchronized(ParcelRecordDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ParcelRecordDatabase::class.java,
                "parcelRecords").build()
        }
    }
    return INSTANCE
}
