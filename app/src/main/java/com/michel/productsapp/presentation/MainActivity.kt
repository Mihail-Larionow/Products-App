package com.michel.productsapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michel.data.api.ProductAPI
import com.michel.data.api.ProductDBClient
import com.michel.data.network.NetworkState
import com.michel.data.repository.ProductsRepository
import com.michel.productsapp.R
import com.michel.productsapp.adapter.ProductListAdapter
import com.michel.productsapp.adapter.PRODUCT_VIEW_TYPE

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var productsRepository: ProductsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val api: ProductAPI = ProductDBClient().getClient()

        productsRepository = ProductsRepository(api)

        viewModel = getViewModel()

        val adapter = ProductListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return if(viewType == PRODUCT_VIEW_TYPE) 1
                else 2
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.productPagedList.observe(this) {
            adapter.submitData(this.lifecycle, it)
        }

        viewModel.networkState.observe(this) {
                adapter.setNetworkState(it)
        }
    }

    private fun getViewModel(): MainViewModel{
        return ViewModelProvider(this, MainViewModelFactory(productsRepository))[MainViewModel::class.java]
    }

}
