package com.michel.productsapp.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.productsapp.data.api.ProductAPI
import com.michel.productsapp.models.NetworkState
import com.michel.productsapp.models.Product
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SingleProductDataSource(private val productAPI: ProductAPI, private val compositeDisposable: CompositeDisposable) {

    private val networkStateMutable = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = networkStateMutable

    private val singleProductMutable = MutableLiveData<Product>()
    val singleProduct: LiveData<Product> = singleProductMutable

    fun fetch(productId: Int){

        networkStateMutable.postValue(NetworkState.LOADING)

        try{
            compositeDisposable.add(
                productAPI.getProductDetails(productId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            singleProductMutable.postValue(it)
                            networkStateMutable.postValue(NetworkState.LOADED)
                        },
                        {
                            networkStateMutable.postValue(NetworkState.ERROR)
                            Log.e("SingleProductResponse", "${it.message}")
                        }
                    )
            )
        }
        catch(e: Exception){
            Log.e("SingleProductResponse", "${e.message}")
        }
    }
}