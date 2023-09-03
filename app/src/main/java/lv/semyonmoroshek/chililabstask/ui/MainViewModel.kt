package lv.semyonmoroshek.chililabstask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lv.semyonmoroshek.chililabstask.data.model.Data
import lv.semyonmoroshek.chililabstask.data.network.ApiClient
import lv.semyonmoroshek.chililabstask.data.repository.GifPagingSource
import lv.semyonmoroshek.chililabstask.data.repository.Repository

class MainViewModel(
//    private val repository: Repository
) : ViewModel() {

    private val repository = Repository()

    private var searchJob: Job? = null

    private val _searchGifResp = MutableLiveData<PagingData<Data>>()
    val searchGifRespData: LiveData<PagingData<Data>> = _searchGifResp
    fun searchGif(query: String): LiveData<PagingData<Data>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                GifPagingSource(ApiClient.apiService, query)
            }
        ).liveData.debounce(300, viewModelScope).cachedIn(viewModelScope)
    }

    fun <T> LiveData<T>.debounce(duration: Long = 1000L, coroutineScope: CoroutineScope) =
        MediatorLiveData<T>().also { mld ->

            val source = this

            mld.addSource(source) {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    delay(duration)
                    mld.value = source.value
                }
            }
        }
}