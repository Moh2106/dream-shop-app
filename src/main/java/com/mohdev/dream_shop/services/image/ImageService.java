package com.mohdev.dream_shop.services.image;

import com.mohdev.dream_shop.dtos.ImageDto;
import com.mohdev.dream_shop.entities.Image;
import com.mohdev.dream_shop.entities.Product;
import com.mohdev.dream_shop.exception.ResourceNotFoundExeption;
import com.mohdev.dream_shop.repositories.ImageRepository;
import com.mohdev.dream_shop.services.product.IProductService;
import com.mohdev.dream_shop.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundExeption("No image found with id " + id)
                );

    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        () -> {throw new ResourceNotFoundExeption("No image found with id "+ id);
                        }
                        );
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> saveImageDtos = new ArrayList<>();

        for (MultipartFile file: files){
            try {
                Image image = Image.builder()
                        .fileName(file.getOriginalFilename())
                        .fileType(file.getContentType())
                        .image(new SerialBlob(file.getBytes()))
                        .product(product)
                        .build();

                String buildDownloadUrl = "/api/v1/images/image/download";
                String downloadUrl = buildDownloadUrl + image.getId();

                image.setDownloadUrl(downloadUrl);

                Image saveImage = imageRepository.save(image);

                saveImage.setDownloadUrl(downloadUrl+ image.getId());

                ImageDto imageDto = ImageDto.builder()
                        .imageId(saveImage.getId())
                        .downloadUrl(saveImage.getDownloadUrl())
                        .imageName(saveImage.getFileName())
                        .build();

                saveImageDtos.add(imageDto);

            }  catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return saveImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) throws IOException, SQLException {
        Image image = getImageById(imageId);
        image.setFileName(file.getOriginalFilename());
        image.setImage(new SerialBlob(file.getBytes()));
        imageRepository.save(image);

    }
}
