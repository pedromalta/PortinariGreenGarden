package br.com.drivin.util

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.HttpException

object Network {

    /**
     * Returns true if the Throwable is an instance of RetrofitError with an http status value equals
     * to the given one.
     */
    fun isHttpStatusCode(throwable: Throwable, statusCode: Int): Boolean {
        return throwable is HttpException && throwable.code() == statusCode
    }

    fun isOnline(context: Context) : Boolean
    {
        val conmag = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        conmag.activeNetworkInfo
        //Verifica o WIFI
        return if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected) {
            true
        } else conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected //Verifica o 3G

    }


    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}