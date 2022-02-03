package cz.utb.fai.ceskapostaapi.network

import com.google.gson.annotations.SerializedName
import cz.utb.fai.ceskapostaapi.database.ParcelRecordEntity
import cz.utb.fai.ceskapostaapi.database.ParcelRecordStateEntity
import cz.utb.fai.ceskapostaapi.models.ParcelRecord
import cz.utb.fai.ceskapostaapi.models.ParcelRecordState

data class ParcelRecordDTO (
    @SerializedName("id")
    val Id: String,

    @SerializedName("states")
    val States: StatesDTO,

    @SerializedName("attributes")
    val Attributes: AttributesDTO
)

data class AttributesDTO (
    @SerializedName("parcelType")
    val ParcelType: String,

    @SerializedName("weight")
    val Weight: Double,

    @SerializedName("dobirka")
    val Cod: Double,

    @SerializedName("ulozniDoba")
    val StorageLength: Int
)

data class StatesDTO (
    @SerializedName("state")
    val States: List<StateDTO>
)

data class StateDTO (
    @SerializedName("id")
    val Id: String,

    @SerializedName("date")
    val Date: String,

    @SerializedName("text")
    val Text: String,

    @SerializedName("postCode")
    val PostCode: String?,

    @SerializedName("postOffice")
    val PostOffice: String?
)

fun List<ParcelRecordDTO>.asDatabaseModel(): List<Pair<ParcelRecordEntity, List<ParcelRecordStateEntity>>> {
    return map { dto ->
        Pair(
            ParcelRecordEntity(
                recordId = null,
                Id = dto.Id,
                ParcelType = dto.Attributes.ParcelType,
                Weight = dto.Attributes.Weight,
                Cod = dto.Attributes.Cod,
                StorageLength = dto.Attributes.StorageLength
            ),
            dto.States.States.map {
                ParcelRecordStateEntity(
                    stateId = null,
                    parcelRecordId = null,
                    Id = it.Id,
                    Date = it.Date,
                    Text = it.Text,
                    PostCode = it.PostCode,
                    PostOffice = it.PostOffice
                )
            }
        )
    }
}

fun ParcelRecordDTO.asModel(): ParcelRecord {
    return ParcelRecord(
        Id = Id,
        ParcelType = Attributes.ParcelType,
        Weight = Attributes.Weight,
        Cod = Attributes.Cod,
        StorageLength = Attributes.StorageLength,
        States = States.States.map {
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