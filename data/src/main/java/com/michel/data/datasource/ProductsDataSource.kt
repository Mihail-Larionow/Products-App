package com.michel.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.michel.data.api.DEFAULT_LIMIT
import com.michel.data.api.ProductAPI
import com.michel.data.api.START_SKIP
import com.michel.data.network.NetworkState
import com.michel.data.model.Product

class ProductsDataSource(
    private val api: ProductAPI,
): PagingSource<Int, Product>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        val skip: Int = params.key ?: START_SKIP
        val limit: Int = DEFAULT_LIMIT

        networkState.postValue(NetworkState.LOADING)

        return try{
            val response = api.getProducts(skip, limit)
            val products = checkNotNull(response.body()).products
            val nextKey = if(products.size < DEFAULT_LIMIT) null else skip + DEFAULT_LIMIT
            networkState.postValue(NetworkState.LOADED)
            LoadResult.Page(products, prevKey = null, nextKey = nextKey)
        }
        catch (e: Exception){
            networkState.postValue(NetworkState.ERROR)
            Log.e("ProductsDataSource", "${e.message}")
            LoadResult.Error(e)
        }

    }

}