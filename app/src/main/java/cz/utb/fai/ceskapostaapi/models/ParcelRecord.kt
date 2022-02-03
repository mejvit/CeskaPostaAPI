package cz.utb.fai.ceskapostaapi.models

data class ParcelRecord(
    val Id: String,
    val ParcelType: String,
    val Weight: Double,
    val Cod: Double,
    val StorageLength: Int,
    val States: List<ParcelRecordState>
)

data class ParcelRecordState (
    val Id: String,
    val Date: String,
    val Text: String,
    val PostCode: String?,
    val PostOffice: String?
)