package lv.semyonmoroshek.chililabstask.data.repository

import android.system.ErrnoException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import lv.semyonmoroshek.chililabstask.data.model.Data
import lv.semyonmoroshek.chililabstask.data.network.API
import lv.semyonmoroshek.chililabstask.data.network.ApiClient

class GifPagingSource(
    private val api: API,
    private val query: String
) : PagingSource<Int, Data>() {

    private val apiKey = "BGuJYFEoriMJZQM51jitfQ6PyAwSul7L"

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val currentPage = params.key ?: 0
        val response = ApiClient.apiService.searchGifs(apiKey, query, currentPage * 10, 10)
        return if (response.isSuccessful) {
            val resp = response.body()!!

            LoadResult.Page<Int, Data>(
                resp.data,
                if (currentPage == 0) null else currentPage - 1,
                if (resp.pagination.offset + resp.pagination.count > resp.pagination.total_count) {
                    null
                } else {
                    currentPage + 1
                }
            )
        } else {
            val unknownException = Exception("WTF")
            LoadResult.Error(unknownException)
        }
    }
}