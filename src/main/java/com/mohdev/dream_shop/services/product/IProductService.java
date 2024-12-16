package com.mohdev.dream_shop.services.product;

import com.mohdev.dream_shop.entities.Product;
import com.mohdev.dream_shop.request.AddProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    void updateProduct(Long productId, Product product);
    List<Product> getAllProducts();
    List<Product> getProductsByCategrory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand, String name);
}
