package com.mohdev.dream_shop.repositories;

import com.mohdev.dream_shop.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
