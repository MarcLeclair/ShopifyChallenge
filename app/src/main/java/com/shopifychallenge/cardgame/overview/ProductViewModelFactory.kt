package com.shopifychallenge.cardgame.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductViewModelFactory(private val numOfRows: Int, private val numOfColumns: Int, private val numOfMatches: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductImageViewModel::class.java)) {
            return ProductImageViewModel(numOfRows, numOfColumns, numOfMatches) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}