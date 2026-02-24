package com.example.entidadesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entidadesapp.data.ProductDao
import com.example.entidadesapp.data.ProductEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(private val productDao: ProductDao) : ViewModel() {

    private val _categoryId = MutableStateFlow<Int?>(null)
    
    val products: StateFlow<List<ProductEntity>> = _categoryId
        .flatMapLatest { id ->
            if (id != null) productDao.getProductsByCategory(id)
            else kotlinx.coroutines.flow.flowOf(emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setCategoryId(id: Int) {
        _categoryId.value = id
    }

    fun addProduct(name: String, categoryId: Int) {
        viewModelScope.launch {
            productDao.insertProduct(ProductEntity(name = name, categoryId = categoryId))
        }
    }

    fun deleteProduct(product: ProductEntity) {
        viewModelScope.launch {
            productDao.deleteProduct(product)
        }
    }
}
