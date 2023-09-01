package lv.semyonmoroshek.chililabstask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lv.semyonmoroshek.chililabstask.data.model.GiphyApiResponse
import lv.semyonmoroshek.chililabstask.data.repository.Repository
import javax.inject.Inject

class MainViewModel (
//    private val repository: Repository
): ViewModel() {

    private val repository = Repository()

    private val _searchGifResp = MutableLiveData<GiphyApiResponse?>()
    val searchGifResp: LiveData<GiphyApiResponse?> = _searchGifResp
    fun searchGif(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            val resp = repository.searchGifs(query)
            _searchGifResp.postValue(resp)
        }
    }
}