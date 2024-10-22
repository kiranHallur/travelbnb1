package com.travelbnb.travelbnb.service;

import com.travelbnb.travelbnb.entity.Image;
import com.travelbnb.travelbnb.entity.Property;
import com.travelbnb.travelbnb.repository.ImageRepository;
import com.travelbnb.travelbnb.repository.PropertyEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceIMPL implements ImageService{

    private ImageRepository imageRepository ;
    private PropertyEntityRepository propertyEntityRepository ;
    private BucketService bucketService ;

    public ImageServiceIMPL(ImageRepository imageRepository, PropertyEntityRepository propertyEntityRepository, BucketService bucketService) {
        this.imageRepository = imageRepository;
        this.propertyEntityRepository = propertyEntityRepository;
        this.bucketService = bucketService;
    }



    @Override
    public Image uploadPropertyPhotos(MultipartFile file, String bucketName, long propertyId) {
        Property property = propertyEntityRepository.findById(propertyId).get();
        String imageUrl= null;
        try {
            imageUrl = bucketService.uploadFile(file,bucketName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image image = new Image();
       image.setProperty(property);
       image.setImageUrl(imageUrl);
        Image saveImageEntity = imageRepository.save(image);
        return saveImageEntity;
    }
}
