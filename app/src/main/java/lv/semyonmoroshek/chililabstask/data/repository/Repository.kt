package lv.semyonmoroshek.chililabstask.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import lv.semyonmoroshek.chililabstask.data.model.ApiError
import lv.semyonmoroshek.chililabstask.data.model.GiphyApiResponse
import lv.semyonmoroshek.chililabstask.data.network.API
import lv.semyonmoroshek.chililabstask.data.network.ApiClient.apiService
import javax.inject.Inject
import javax.inject.Singleton

class Repository(
//    private val api: API
) {
//    companion object {
//        @Volatile
//        private var instance: Repository? = null
//        fun getInstance(
//            api: API,
//        ) =
//            instance ?: synchronized(this) {
//                instance ?: Repository(api).also {
//                    instance = it
//                }
//            }
//    }

    var repositoryError = MutableLiveData(ApiError())


    suspend fun searchGifs(query: String): GiphyApiResponse? {
        val apiKey = "BGuJYFEoriMJZQM51jitfQ6PyAwSul7L"
        val response = apiService.searchGifs(apiKey, query)
        return if (response.isSuccessful) {
            response.body()
        } else {
            postError(response.message())
            null
        }
    }

    private fun postError(errorMsg: String) {
        repositoryError.postValue(ApiError(msg = errorMsg))
    }

}