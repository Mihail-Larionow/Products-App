package com.michel.productsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.michel.data.model.Product
import com.michel.data.network.NetworkState
import com.michel.productsapp.R
import com.michel.productsapp.presentation.single.SingleProduct

const val PRODUCT_VIEW_TYPE = 100
const val NETWORK_VIEW_TYPE = 200

class ProductListAdapter(private val context: Context): PagingDataAdapter<Product, RecyclerView.ViewHolder>(ProductDiffCallback()) {

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == PRODUCT_VIEW_TYPE){
            (holder as ProductItemViewHolder).bind(getItem(position), context)
        }
        else{
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        return if(viewType == PRODUCT_VIEW_TYPE){
            view = layoutInflater.inflate(R.layout.product_list_item, parent, false)
            ProductItemViewHolder(itemView = view)
        } else{
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            NetworkStateItemViewHolder(itemView = view)
        }
    }
    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position == itemCount - 1){
            NETWORK_VIEW_TYPE
        }else{
            PRODUCT_VIEW_TYPE
        }
    }

    fun setNetworkState(newNetworkState: NetworkState){
        val previousState: NetworkState? = this.networkState
        val hadExtraRow: Boolean = hasExtraRow()

        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if(hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        }else if(hasExtraRow && previousState != newNetworkState){
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow(): Boolean{
        return networkState != null && networkState != NetworkState.LOADED
    }

}

class ProductItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(product: Product?, context: Context){
        itemView.findViewById<TextView>(R.id.product_title).text = product?.title
        itemView.findViewById<TextView>(R.id.product_description).text = product?.description

        Glide.with(itemView.context)
            .load(product?.thumbnail)
            .into(itemView.findViewById(R.id.product_thumbnail))

        itemView.setOnClickListener{
            val intent = Intent(context, SingleProduct::class.java)
            intent.putExtra("productId", product?.id)
            context.startActivity(intent)
        }
    }
}

class NetworkStateItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(networkState: NetworkState?){
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)

        if(networkState != null && networkState == NetworkState.LOADING){
            progressBar.visibility = View.VISIBLE
        }
        else{
            progressBar.visibility = View.GONE
        }
    }
}

class ProductDiffCallback: DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

}