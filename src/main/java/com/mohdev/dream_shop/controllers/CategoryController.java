package com.mohdev.dream_shop.controllers;

import com.mohdev.dream_shop.entities.Category;
import com.mohdev.dream_shop.exception.AlreadyExistException;
import com.mohdev.dream_shop.exception.ResourceNotFoundExeption;
import com.mohdev.dream_shop.response.ApiResponse;
import com.mohdev.dream_shop.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();

        try {
            return ResponseEntity.ok(
                    new ApiResponse("Found", categories)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR)
                    );
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        try {
            Category category1 = categoryService.addCategory(category);

            return ResponseEntity.ok(
                    new ApiResponse("Success", category1)
            );
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(
                            new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)
                    );
        }
    }

    @GetMapping("/{categoryId}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try {
            Category category = categoryService.getCategoryById(categoryId);

            return ResponseEntity.ok(
                    new ApiResponse("Found", category)
            );
        } catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @GetMapping("/{name}/category-name")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category category = categoryService.getCategoryByName(name);

            return ResponseEntity.ok(
                    new ApiResponse("Found", category)
            );
        } catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @DeleteMapping("/{categoryId}/category")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        try {
            categoryService.deleteCategory(categoryId);

            return ResponseEntity.ok(
                    new ApiResponse("Found", null)
            );
        } catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(e.getMessage(), null)
                    );
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category){
        try {
            Category category1 = categoryService.updateCategory(id, category);

            return ResponseEntity.ok(
                    new ApiResponse("Update Success", category1)
            );
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(
                            new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)
                    );
        }
    }


}
