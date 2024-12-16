package com.mohdev.dream_shop.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageDto {
    private Long imageId;
    private String imageName;
    private String downloadUrl;
}
