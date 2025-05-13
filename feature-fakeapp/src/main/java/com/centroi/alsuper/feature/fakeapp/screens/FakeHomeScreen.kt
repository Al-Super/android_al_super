package com.centroi.alsuper.feature.fakeapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.centroi.alsuper.feature.fakeapp.viewmodel.ProductViewModel

@Composable
fun FakeHomeScreen() {
    ProductListScreen()
}

@Composable
fun ProductListScreen(viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMockProducts() // just for demo
    }

    LazyColumn {
        items(products) { product ->
            Text(product.name)
        }
    }
}

