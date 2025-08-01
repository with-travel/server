package com.arom.with_travel.domain.image.repository;

import com.arom.with_travel.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
