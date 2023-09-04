package lv.semyonmoroshek.chililabstask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lv.semyonmoroshek.chililabstask.data.model.Data
import lv.semyonmoroshek.chililabstask.data.network.API
import lv.semyonmoroshek.chililabstask.data.repository.GifPagingSource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: API
) : ViewModel() {

    private var searchJob: Job? = null

    fun searchGif(query: String): LiveData<PagingData<Data>> {
        return if (query.isBlank()) {
            searchJob?.cancel()
            liveData {
                emit(PagingData.empty())
            }
        } else {
            Pager(
                config = PagingConfig(10),
                pagingSourceFactory = {
                    GifPagingSource(api, query)
                }
            ).liveData.debounce(300, viewModelScope).cachedIn(viewModelScope)
        }
    }

    private fun <T> LiveData<T>.debounce(duration: Long = 1000L, coroutineScope: CoroutineScope) =
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