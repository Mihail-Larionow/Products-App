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
import com.michel.data.api.ProductAPI
import com.michel.data.api.ProductDBClient
import com.michel.data.network.NetworkState
import com.michel.data.repository.SingleProductRepository
import com.michel.data.model.Product
import com.michel.productsapp.R

class SingleProduct : AppCompatActivity() {

    private lateinit var viewModel: SingleProductViewModel
    private lateinit var singleProductRepository: SingleProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val productId = intent.getIntExtra("productId", 1)

        val productDB: ProductAPI = ProductDBClient().getClient()
        singleProductRepository = SingleProductRepository(productDB)

        viewModel = getViewModel(productId)

        viewModel.singleProduct.observe(this) {
            bindUI(it)
        }

        viewModel.networkState.observe(this) {
            findViewById<ProgressBar>(R.id.progress_bar).visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            findViewById<TextView>(R.id.error_text).visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        }
    }

    private fun bindUI(product: Product){
        findViewById<TextView>(R.id.product_title).text = product.title
        findViewById<TextView>(R.id.product_description).text = product.title

        Glide.with(this)
            .load(product.thumbnail)
            .into(findViewById(R.id.product_image))
    }

    private fun getViewModel(productId: Int): SingleProductViewModel{
        return ViewModelProvider(this, SingleProductViewModelFactory(singleProductRepository, productId))[SingleProductViewModel::class.java]
    }


}