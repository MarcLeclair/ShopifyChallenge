package com.shopifychallenge.cardgame.network

import com.squareup.moshi.Json


data class Products(
        @Json(name = "products") val products: List<Product>
)

data class Product(
        @Json(name="id")
        val id: String,

        @Json(name="title")
        val title: String,

        @Json(name = "image")
        val imgSrcUrl: ProductImg)

data class  ProductImg(

        @Json(name="id")
        val id: String,

        @Json(name="width")
        val width: Int,

        @Json(name="height")
        val height: Int,

        @Json(name="alt")
        val alt: String,

        @Json(name="src")
        val src: String
)
