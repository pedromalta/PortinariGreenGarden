package br.com.drivin

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import br.com.drivin.api.PortinariApis
import br.com.drivin.api.IPortinariApi
import com.readystatesoftware.chuck.ChuckInterceptor
import com.tspoon.traceur.Traceur
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception

class PortinariHome : Application() {

    companion object {
        lateinit var instance: PortinariHome
        lateinit var api: IPortinariApi
        lateinit var httpClient: OkHttpClient
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        configClient()
        configApisUrl()
        configFonts()
        configLocalDb()

        if (BuildConfig.DEBUG) {
            Traceur.enableLogging()
        }

    }

    private fun configClient() {
        val clientBuilder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(configTimeoutInterceptor())


        if (getPreferenceBoolean(Constants.Preferences.HTTP_DEBUGGER)) {
            clientBuilder.addInterceptor(ChuckInterceptor(this)) //only debug
            clientBuilder.addInterceptor(httpLoggingInterceptor())
        }

        httpClient = clientBuilder.build()
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d("HTTP", message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    private fun configFonts() {

    }

    private fun configLocalDb() {
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .name("drivin")
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                //.initialData(Seed())
                .build()
        Realm.setDefaultConfiguration(realmConfig)
    }


    private fun configTimeoutInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()

            var connectTimeout = chain.connectTimeoutMillis()

            val connectNew = request.header(IPortinariApi.TIMEOUT)

            if (!connectNew.isNullOrEmpty()) {
                connectTimeout = connectNew.toInt()
            }

            val builder = request.newBuilder()
            builder.removeHeader(IPortinariApi.TIMEOUT)

            chain
                    .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                    .proceed(builder.build())
        }
    }

    /**
     * Pegamos a codigo da URL da API das preferencias e caso nao exista do BuildConfig
     * Esse ID eh passado para um MAP que retorna as URLs das APIs
     * As preferencia podem ser configuradas no menu backdoor acessado pelo discador do telefone
     * @see {@link br.com.vix.v1motorista.services.configuracoes.DiscadorReceiver}
     */
    private fun configApisUrl() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val apiUrl = preferences.getString(Constants.Preferences.API_URL, BuildConfig.DRIVIN_API_URL)!!
        api = PortinariApis().getMiddleware(httpClient, apiUrl)
    }

    fun getPreferenceBoolean(pref: String): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        when (pref) {
            Constants.Preferences.HTTP_DEBUGGER -> return preferences.getBoolean(pref, resources.getBoolean(R.bool.HTTP_DEBUGGER))
            Constants.Preferences.OVERLAY_DEBUGGER -> return preferences.getBoolean(pref, resources.getBoolean(R.bool.OVERLAY_DEBUGGER))
        }
        throw Exception("Preferencia nao existe")
    }

}