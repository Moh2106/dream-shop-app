package com.mohdev.dream_shop.request;

import com.mohdev.dream_shop.entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private  int inventory;
    private String description;
    private Category category;
}
