package com.shopifychallenge.cardgame.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopifychallenge.cardgame.network.RetrofitApi
import com.shopifychallenge.cardgame.network.Product
import com.shopifychallenge.cardgame.network.ProductImg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiServiceStatus { LOADING, ERROR, DONE }


class ProductImageViewModel(private val numOfRows : Int, private val numOfColumns : Int , private val numOfMatches: Int) : ViewModel() {

    private val ACCESS_TOKEN = "c32313df0d0ef512ca64d5b336a0d7c6"
    private val PAGE = 1

    private var _numOfColumns : Int  = 0
    private var _numOfRows : Int  = 0


    private val _status = MutableLiveData<ApiServiceStatus>()

    val serviceStatus: LiveData<ApiServiceStatus>
        get() = _status


    private val _properties = MutableLiveData<List<ProductImg>>()


    val properties: LiveData<List<ProductImg>>
        get() = _properties


    private var _cardToCompare : ProductImg ?= null

    private var _listToFlipBack = MutableLiveData<List<Int>>()

    val listToFlipBack : LiveData<List<Int>>
        get() = _listToFlipBack


    private var _won = MutableLiveData<Boolean>()

    private var counter : Int = 0

    val won : LiveData<Boolean>
        get() = _won

    private var _maxTries : Int = 0
    private var _currentTries : Int = 0

    private var _list : MutableList<Int> = mutableListOf()


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _won.value = false
        _numOfColumns = numOfColumns
        _numOfRows = numOfRows
        _maxTries = numOfMatches
        getAllShopifyProducts()
    }

    private fun getAllShopifyProducts() {
        coroutineScope.launch {
            _status.value = ApiServiceStatus.LOADING
            // this will run on a thread managed by Retrofit
            var getPropertiesDeferred = RetrofitApi.RETROFIT_SERVICE.getProducts(PAGE, ACCESS_TOKEN)
            try {
                _status.value = ApiServiceStatus.LOADING
                // this will run on a thread managed by Retrofit
                var listResult = getPropertiesDeferred.await()


                var listProduct: List<Product> = mutableListOf()
                var listImage: MutableList<ProductImg> = mutableListOf()

                listProduct = listResult.products.shuffled()

                listProduct.forEachIndexed{ index, elem ->
                    if(index > ((numOfRows * numOfColumns) / numOfMatches)) return@forEachIndexed
                    listImage.add(elem.imgSrcUrl)
                }

                var finalProductImgList : List<ProductImg> = mutableListOf<ProductImg>().apply{
                    addAll(listImage)
                    addAll(listImage)
                }

                finalProductImgList = finalProductImgList.shuffled()

                _status.value = ApiServiceStatus.DONE
                _properties.value =finalProductImgList
            } catch (e: Exception) {
                _status.value = ApiServiceStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun flipCard(productImg : ProductImg, position : Int) {

        _cardToCompare?.let{
            _list.add(position)
            _currentTries += 1
            if(productImg.src != _cardToCompare?.src) {
                _cardToCompare = null
                _listToFlipBack.value = mutableListOf<Int>().apply {
                    addAll(_list)
                }
                _currentTries = 0
                _list.clear()
            }
        } ?: run{
            _cardToCompare = productImg
            _list.add(position)
            _currentTries += 1
        }

        if(_currentTries == _maxTries){
            counter += _list.size
            _list.clear()
            _cardToCompare = null


        }

        if(counter == (_numOfColumns * _numOfRows)){
            _won.value = true
        }
    }
}


