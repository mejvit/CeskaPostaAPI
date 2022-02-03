package cz.utb.fai.ceskapostaapi.database

import androidx.room.*
import cz.utb.fai.ceskapostaapi.models.ParcelRecord
import cz.utb.fai.ceskapostaapi.models.ParcelRecordState

@Entity
data class ParcelRecordEntity (
    @PrimaryKey val recordId: Long?,
    val Id: String,
    val ParcelType: String,
    val Weight: Double,
    val Cod: Double,
    val StorageLength: Int
)

@Entity
data class ParcelRecordStateEntity (
    @PrimaryKey val stateId: Long?,
    var parcelRecordId: Long?,
    val Id: String,
    val Date: String,
    val Text: String,
    val PostCode: String?,
    val PostOffice: String?
)

data class ParcelRecordWithStates (
    @Embedded val ParcelRecord: ParcelRecordEntity,
    @Relation (
        parentColumn = "recordId",
        entityColumn = "parcelRecordId"
    )
    val States: List<ParcelRecordStateEntity>
)

fun ParcelRecordWithStates.asModel(): ParcelRecord {
    return ParcelRecord(
        Id = ParcelRecord.Id,
        ParcelType = ParcelRecord.ParcelType,
        Weight = ParcelRecord.Weight,
        Cod = ParcelRecord.Cod,
        StorageLength = ParcelRecord.StorageLength,
        States = States.map {
            ParcelRecordState(
                Id = it.Id,
                Date = it.Date,
                Text = it.Text,
                PostCode = it.PostCode,
                PostOffice = it.PostOffice
            )
        }
    )
}

fun List<ParcelRecordWithStates>.asModel(): List<ParcelRecord> {
    return map {
        it.asModel()
    }
}
