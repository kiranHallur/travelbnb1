package com.travelbnb.travelbnb.repository;

import com.travelbnb.travelbnb.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}