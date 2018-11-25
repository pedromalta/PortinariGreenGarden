package br.com.drivin.api

import br.com.drivin.model.responses.*
import io.reactivex.Single
import retrofit2.http.*

interface IPortinariApi {

    companion object {
        const val TIMEOUT = "timeouts"
    }

    @GET("sprinkler/{action}/{sprinkler}")
    fun sprinklers(@Path("start") action: String, @Path("start") sprinkler: Int? = null): Single<SprinklerStatus>



}