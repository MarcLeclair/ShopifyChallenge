package com.shopifychallenge.cardgame.overview

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shopifychallenge.cardgame.databinding.CardBinding

import com.shopifychallenge.cardgame.network.Product
import com.shopifychallenge.cardgame.network.ProductImg
import com.wajahatkarim3.easyflipview.EasyFlipView

class PhotoGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<ProductImg, PhotoGridAdapter.ProductImgViewHolder>(DiffCallback) {

    inner class ProductImgViewHolder(private var binding: CardBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(product: ProductImg) = with(
            binding){
            binding.product = product



            itemView.setOnClickListener{

                if(!binding.flippingcard.isBackSide){
                    binding.flippingcard.setOnFlipListener{view, state ->
                        if(state == EasyFlipView.FlipState.BACK_SIDE){
                            Log.e("done loading", adapterPosition.toString())
                            onClickListener.onClick(adapterPosition, product)
                        }else{

                        }
                    }
                    Log.e("flippin forward", adapterPosition.toString())
                    binding.flippingcard.flipTheView()
                }
            }
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProductImg>() {
        override fun areItemsTheSame(oldItem: ProductImg, newItem: ProductImg): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductImg, newItem: ProductImg): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ProductImgViewHolder {

        return ProductImgViewHolder(CardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProductImgViewHolder, position: Int) {
        val productImg = getItem(position)
        holder.bind(productImg)
    }



    class OnClickListener(val clickListener: (position : Int, productImg : ProductImg) -> Unit) {
        fun onClick(position : Int, productImg : ProductImg) = clickListener(position, productImg)
    }


}

