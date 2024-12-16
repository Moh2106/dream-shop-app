package com.mohdev.dream_shop.services.image;

import com.mohdev.dream_shop.dtos.ImageDto;
import com.mohdev.dream_shop.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId) throws IOException, SQLException;
}
