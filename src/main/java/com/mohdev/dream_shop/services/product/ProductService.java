package com.mohdev.dream_shop.services.product;

import com.mohdev.dream_shop.entities.Category;
import com.mohdev.dream_shop.entities.Product;
import com.mohdev.dream_shop.exception.ProductNotFoundException;
import com.mohdev.dream_shop.exception.ResourceNotFoundExeption;
import com.mohdev.dream_shop.repositories.CategoryRepository;
import com.mohdev.dream_shop.repositories.ProductRepository;
import com.mohdev.dream_shop.request.AddProductRequest;
import com.mohdev.dream_shop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // Check if the category is found in the database
        // If yes set is as the new product category
        // If No, then save it as a new category
        // Then set it as the new product category
        Category category = Optional.ofNullable(categoryRepository
                .findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category category1 = Category.builder()
                            .name(request.getCategory().getName())
                            .build();

                    return categoryRepository.save(category1);
                });

        request.setCategory(category);

        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return Product.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .price(request.getPrice())
                .description(request.getDescription())
                .inventory(request.getInventory())
                .category(category)
                .build();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new ProductNotFoundException("Product not found");
                        }
                        );
    }

    @Override
    public Product updateProduct(Long productId, ProductUpdateRequest product) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, product))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundExeption("Product not found"))
                ;
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());

        existingProduct.setCategory(category);

        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategrory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
