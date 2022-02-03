package cz.utb.fai.ceskapostaapi.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CPostService {
    @GET("/services/ParcelHistory/getDataAsJson")
    suspend fun getParcelRecord(@Query("idParcel") id: String): Response<List<ParcelRecordDTO>>
}
object CPostApiProvider {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://b2c.cpost.cz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val parcelRecords = retrofit.create(CPostService::class.java)
}