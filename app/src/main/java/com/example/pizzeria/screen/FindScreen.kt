package com.example.pizzeria.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pizzeria.model.ProductData
import com.example.pizzeria.viewmodel.ProductViewModel

@Composable
fun FindScreen(
    searchQuery: String,
    navController: NavHostController,
    productViewModel: ProductViewModel = viewModel()
) {
    // Fetch products based on search query
    productViewModel.searchProducts(searchQuery)

    // Observe the filtered products
    val filteredProducts by productViewModel.filteredProducts.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(filteredProducts) { product ->
                ProductItem(product = product)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(product.productImage),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = product.productName ?: "", fontSize = 20.sp, color = Color.Black)
            Text(text = product.productDescription ?: "", fontSize = 14.sp, color = Color.Gray)
            Text(text = "\$${product.productPrice}", fontSize = 16.sp, color = Color.Green)
        }
    }
}
