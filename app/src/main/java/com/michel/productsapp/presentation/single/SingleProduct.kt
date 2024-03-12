package com.michel.productsapp.presentation.single

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.michel.productsapp.data.api.ProductAPI
import com.michel.productsapp.data.api.ProductAPIClient
import com.michel.productsapp.models.NetworkState
import com.michel.productsapp.data.repository.SingleProductRepository
import com.michel.productsapp.models.Product
import com.michel.productsapp.R

class SingleProduct : AppCompatActivity() {

    private lateinit var viewModel: SingleProductViewModel
    private lateinit var singleProductRepository: SingleProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_product)

        val productId = intent.getIntExtra("productId", 1)

        val productAPI: ProductAPI = ProductAPIClient().getClient()
        singleProductRepository = SingleProductRepository(productAPI = productAPI)

        viewModel = getViewModel(productId = productId)

        viewModel.singleProduct.observe(this) {
            bindUI(product = it)
        }

        viewModel.networkState.observe(this) {
            findViewById<ProgressBar>(R.id.progress_bar).visibility =
                if (it == NetworkState.LOADING || it == NetworkState.ERROR)
                    View.VISIBLE else View.GONE
        }
    }

    private fun bindUI(product: Product){
        findViewById<TextView>(R.id.product_brand).text = product.brand
        findViewById<TextView>(R.id.product_price).text = "${product.price}$ (${product.discountPercentage}%)"
        findViewById<TextView>(R.id.product_title).text = product.title
        findViewById<TextView>(R.id.product_description).text = product.description

        Glide.with(this)
            .load(product.thumbnail)
            .into(findViewById(R.id.product_image))
    }

    private fun getViewModel(productId: Int): SingleProductViewModel{
        return ViewModelProvider(this, SingleProductViewModelFactory(singleProductRepository, productId))[SingleProductViewModel::class.java]
    }

}