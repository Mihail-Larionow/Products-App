package com.michel.productsapp.presentation.single

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory.Companion
import com.bumptech.glide.Glide
import com.michel.data.api.IProductDB
import com.michel.data.api.ProductDBClient
import com.michel.data.network.NetworkState
import com.michel.data.repository.ProductDetailsRepository
import com.michel.data.value.Product
import com.michel.productsapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingleProduct : AppCompatActivity() {

    private lateinit var viewModel: SingleProductViewModel
    private lateinit var productRepository: ProductDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val productId = intent.getIntExtra("id", 1)

        val productDB: IProductDB = ProductDBClient().getClient()
        productRepository = ProductDetailsRepository(productDB)

        viewModel = getViewModel(productId)

        viewModel.productDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            findViewById<ProgressBar>(R.id.progress_bar).visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            findViewById<TextView>(R.id.error_text).visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(it: Product){
        findViewById<TextView>(R.id.product_title).text = it.title
        findViewById<TextView>(R.id.product_description).text = it.title

        Glide.with(this)
            .load(it.thumbnail)
            .into(findViewById<ImageView>(R.id.product_image))
    }

    private fun getViewModel(productId: Int): SingleProductViewModel{
        return ViewModelProvider(this, SingleProductViewModelFactory(productRepository, productId))
            .get(SingleProductViewModel::class.java)
    }


}