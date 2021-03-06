package com.example.jet2assignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Vishal Kharat on 10/09/2020.
 * All are singleton class
 */
object ConstantData {

}

object Util{
	fun provideRetrofit(gson: Gson = GsonBuilder().create()) : Retrofit = Retrofit.Builder()
		.baseUrl("https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/")
		.addConverterFactory(GsonConverterFactory.create(gson))
		.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
		.build()
}

object NetworkUtils {
	fun isNetworkConnected(context: Context): Boolean {
		var result = false
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			val networkCapabilities = connectivityManager.activeNetwork?:return false
			val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities)?:return false
			result = when{
				activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
				activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
				activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
				else -> false
			}
		}else{
			@Suppress("DEPRECATION")
			connectivityManager.run {
				connectivityManager.activeNetworkInfo?.run {
					result = when(type){
						ConnectivityManager.TYPE_WIFI -> true
						ConnectivityManager.TYPE_MOBILE -> true
						ConnectivityManager.TYPE_ETHERNET -> true
						else -> false
					}
				}
			}
		}
		return result
	}
}