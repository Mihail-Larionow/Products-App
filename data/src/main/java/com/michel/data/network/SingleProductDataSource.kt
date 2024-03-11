package com.michel.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.data.api.IProductDB
import com.michel.data.value.Product
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SingleProductDataSource(private val iProductDB: IProductDB, private val compositeDisposable: CompositeDisposable) {

    private val networkStateMutable = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = networkStateMutable

    private val productDetailsResponseMutable = MutableLiveData<Product>()
    val productDetailsResponse: LiveData<Product> = productDetailsResponseMutable

    fun fetchProductDetails(productId: Int){

        networkStateMutable.postValue(NetworkState.LOADING)

        try{
            compositeDisposable.add(
                iProductDB.getProductDetails(productId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            productDetailsResponseMutable.postValue(it)
                            networkStateMutable.postValue(NetworkState.LOADED)
                        },
                        {
                            networkStateMutable.postValue(NetworkState.ERROR)
                            Log.e("ProductResponse", it.message!!)
                        }
                    )
            )
        }
        catch(e: Exception){
            Log.e("ProductResponse", "exception")
        }
    }
}