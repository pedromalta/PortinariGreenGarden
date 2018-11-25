package pedromalta.portinari.home.api

import pedromalta.portinari.home.model.responses.*
import io.reactivex.Single
import retrofit2.http.*

interface IPortinariApi {

    companion object {
        const val TIMEOUT = "timeouts"
    }

    @GET("sprinkler/{action}/{sprinkler}")
    fun sprinklers(@Path("action") action: String, @Path("sprinkler") sprinkler: Int): Single<SprinklerStatus>


    @GET("sprinkler/status")
    fun status(): Single<SprinklerStatus>



}