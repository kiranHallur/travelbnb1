package com.travelbnb.travelbnb.service;

import com.travelbnb.travelbnb.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uploadPropertyPhotos(MultipartFile file, String bucketName, long propertyId);
}
