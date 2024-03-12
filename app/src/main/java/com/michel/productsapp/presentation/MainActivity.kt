package com.michel.productsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michel.productsapp.R
import com.michel.productsapp.presentation.adapter.ProductListAdapter
import com.michel.productsapp.presentation.adapter.PRODUCT_VIEW_TYPE
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DEFAULT_SPAN_COUNT = 2
private const val DEFAULT_ITEM_SPAN = 1

class MainActivity : AppCompatActivity() {

    // ViewModel injection using Koin
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ProductListAdapter(context = this)

        val gridLayoutManager = GridLayoutManager(this, DEFAULT_SPAN_COUNT)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position = position)
                return if(viewType == PRODUCT_VIEW_TYPE) DEFAULT_ITEM_SPAN
                else DEFAULT_SPAN_COUNT
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.productPagedList.observe(this) {
            adapter.submitData(this.lifecycle, it)
        }

        viewModel.networkState.observe(this) {
            adapter.setNetworkState(newNetworkState = it)
        }
    }

}
