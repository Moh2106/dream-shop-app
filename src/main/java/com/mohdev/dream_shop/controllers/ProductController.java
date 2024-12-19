package com.mohdev.dream_shop.controllers;

import com.mohdev.dream_shop.entities.Product;
import com.mohdev.dream_shop.exception.ResourceNotFoundExeption;
import com.mohdev.dream_shop.request.AddProductRequest;
import com.mohdev.dream_shop.request.ProductUpdateRequest;
import com.mohdev.dream_shop.response.ApiResponse;
import com.mohdev.dream_shop.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(
                new ApiResponse("success", products)
        );
    }

    @GetMapping("/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(
                    new ApiResponse("success", product)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @GetMapping("/brand-and-name/product")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,
                                                                @RequestParam String productName){

        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);

            if (products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                new ApiResponse("No product found with name "+productName, null)
                        );
            }
            return ResponseEntity.ok(
                    new ApiResponse("success", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @GetMapping("/brand-and-category/product")
    public ResponseEntity<ApiResponse> getProductByBrandAndCategory(@RequestParam String brandName,
                                                                @RequestParam String categoryName){

        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, categoryName);

            if (products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                new ApiResponse("No product found with name " +categoryName, null)
                        );
            }
            return ResponseEntity.ok(
                    new ApiResponse("success", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @GetMapping("/{name}/product")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductByName(name);

            if (products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                new ApiResponse("No product found with name " +name, null)
                        );
            }

            return ResponseEntity.ok(
                    new ApiResponse("success", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @GetMapping("/{brand}/product")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);

            if (products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                new ApiResponse("No product found with brand  " +brand, null)
                        );
            }

            return ResponseEntity.ok(
                    new ApiResponse("success", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @GetMapping("/{category}/product/all/category")
    public ResponseEntity<ApiResponse> findProductsByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategrory(category);

            if (products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(
                                new ApiResponse("No product found with category  " +category, null)
                        );
            }

            return ResponseEntity.ok(
                    new ApiResponse("success", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @GetMapping("/count/by-brand-and-name/product")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brandName,
                                                                  @RequestParam String productName){
        try {
            Long productCount = productService.countProductByBrandAndName(brandName, productName);


            return ResponseEntity.ok(
                    new ApiResponse("success", productCount)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product product1 = productService.addProduct(product);

            return ResponseEntity.ok(
                    new ApiResponse("Add product success", product1)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,
                                                     @PathVariable Long id){
        try {
            Product product = productService.updateProduct(id, request);
            return ResponseEntity.ok(
                    new ApiResponse("Update product success", product)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProductById(id);

            return ResponseEntity.ok(
                    new ApiResponse("Update product success", id)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }


}
