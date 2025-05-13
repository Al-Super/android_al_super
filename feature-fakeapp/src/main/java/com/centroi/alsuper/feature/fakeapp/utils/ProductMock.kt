package com.centroi.alsuper.feature.fakeapp.utils

import com.centroi.alsuper.core.database.tables.Product
import com.centroi.alsuper.core.database.tables.ProductCategory

val mockProducts = listOf(
    // Electronics
    Product(1, "Smartphone X1", "Flagship Android phone", 799.99, "https://example.com/img1.jpg", ProductCategory.Electronics.id),
    Product(2, "Laptop Pro 15", "High performance laptop", 1199.99, "https://example.com/img2.jpg", ProductCategory.Electronics.id),
    Product(3, "Wireless Earbuds", "Noise-canceling earbuds", 129.99, "https://example.com/img3.jpg", ProductCategory.Electronics.id),
    Product(4, "Smartwatch S", "Fitness tracker with AMOLED display", 199.99, "https://example.com/img4.jpg", ProductCategory.Electronics.id),
    Product(5, "Tablet Z", "10-inch tablet for work and play", 349.99, "https://example.com/img5.jpg", ProductCategory.Electronics.id),
    Product(6, "Bluetooth Speaker", "Portable speaker with bass", 59.99, "https://example.com/img6.jpg", ProductCategory.Electronics.id),
    Product(7, "Gaming Mouse", "Ergonomic design with RGB", 49.99, "https://example.com/img7.jpg", ProductCategory.Electronics.id),
    Product(8, "4K Monitor", "27-inch UHD display", 299.99, "https://example.com/img8.jpg", ProductCategory.Electronics.id),
    Product(9, "External SSD", "1TB high-speed storage", 139.99, "https://example.com/img9.jpg", ProductCategory.Electronics.id),
    Product(10, "USB-C Hub", "Multiport adapter", 29.99, "https://example.com/img10.jpg", ProductCategory.Electronics.id),

    // Clothing
    Product(11, "Men's T-Shirt", "Cotton round-neck shirt", 15.99, "https://example.com/img11.jpg", ProductCategory.Clothing.id),
    Product(12, "Women's Blouse", "Chiffon long-sleeve blouse", 25.99, "https://example.com/img12.jpg", ProductCategory.Clothing.id),
    Product(13, "Jeans", "Slim fit denim pants", 39.99, "https://example.com/img13.jpg", ProductCategory.Clothing.id),
    Product(14, "Sneakers", "Unisex running shoes", 59.99, "https://example.com/img14.jpg", ProductCategory.Clothing.id),
    Product(15, "Jacket", "Waterproof windbreaker", 79.99, "https://example.com/img15.jpg", ProductCategory.Clothing.id),
    Product(16, "Dress", "Casual floral print", 45.99, "https://example.com/img16.jpg", ProductCategory.Clothing.id),
    Product(17, "Cap", "Adjustable baseball cap", 12.99, "https://example.com/img17.jpg", ProductCategory.Clothing.id),
    Product(18, "Socks (5-pack)", "Comfort cotton socks", 9.99, "https://example.com/img18.jpg", ProductCategory.Clothing.id),
    Product(19, "Hoodie", "Pullover fleece hoodie", 35.99, "https://example.com/img19.jpg", ProductCategory.Clothing.id),
    Product(20, "Belt", "Leather casual belt", 22.99, "https://example.com/img20.jpg", ProductCategory.Clothing.id),

    // Home Appliances
    Product(21, "Microwave Oven", "800W countertop microwave", 89.99, "https://example.com/img21.jpg", ProductCategory.HomeAppliances.id),
    Product(22, "Blender", "Smoothie blender with 3 speeds", 49.99, "https://example.com/img22.jpg", ProductCategory.HomeAppliances.id),
    Product(23, "Coffee Maker", "Drip coffee machine", 59.99, "https://example.com/img23.jpg", ProductCategory.HomeAppliances.id),
    Product(24, "Vacuum Cleaner", "Bagless upright vacuum", 129.99, "https://example.com/img24.jpg", ProductCategory.HomeAppliances.id),
    Product(25, "Toaster", "2-slice stainless steel", 29.99, "https://example.com/img25.jpg", ProductCategory.HomeAppliances.id),
    Product(26, "Air Fryer", "Healthy oil-free cooking", 99.99, "https://example.com/img26.jpg", ProductCategory.HomeAppliances.id),
    Product(27, "Iron", "Steam iron with ceramic soleplate", 34.99, "https://example.com/img27.jpg", ProductCategory.HomeAppliances.id),
    Product(28, "Heater", "Portable electric heater", 69.99, "https://example.com/img28.jpg", ProductCategory.HomeAppliances.id),
    Product(29, "Ceiling Fan", "3-speed with remote", 89.99, "https://example.com/img29.jpg", ProductCategory.HomeAppliances.id),
    Product(30, "Washing Machine", "Top-load 7kg", 399.99, "https://example.com/img30.jpg", ProductCategory.HomeAppliances.id),
)
