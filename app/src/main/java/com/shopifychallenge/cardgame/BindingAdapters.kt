package com.shopifychallenge.cardgame


import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shopifychallenge.cardgame.network.ProductImg
import com.shopifychallenge.cardgame.overview.ApiServiceStatus
import com.shopifychallenge.cardgame.overview.PhotoGridAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ProductImg>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("entries", "itemLayout", "textViewId", requireAll = false)
fun AutoCompleteTextView.bindAdapter(entries: Array<Any?>, @LayoutRes itemLayout: Int?, @IdRes textViewId: Int?) {
    val adapter = when {
        itemLayout == null -> {
            ArrayAdapter(context, R.layout.drop_down_menu, R.id.dropDown, entries)
        }
        textViewId == null -> {
            ArrayAdapter(context, itemLayout, entries)
        }
        else -> {
            ArrayAdapter(context, itemLayout, textViewId, entries)
        }
    }
    this.setAdapter(adapter)
}



@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)

    }
}

@BindingAdapter("gameApiStatus")
fun bindStatus(statusImageView: ImageView, serviceStatus: ApiServiceStatus?) {
    when (serviceStatus) {
        ApiServiceStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiServiceStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiServiceStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
