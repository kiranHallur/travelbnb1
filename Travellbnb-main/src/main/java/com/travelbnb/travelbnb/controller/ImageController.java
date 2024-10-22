package com.travelbnb.travelbnb.controller;


import com.travelbnb.travelbnb.entity.AppUser;
import com.travelbnb.travelbnb.entity.Image;
import com.travelbnb.travelbnb.service.ImageService;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/Image")
public class ImageController {

    private ImageService imageService ;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    //http://localhost:8080/api/v1/Image/upload/file/tavelbnb01/property/1

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image>uploadPropertyPhotos(
            @RequestParam MultipartFile file ,
            @PathVariable String bucketName,
            @PathVariable long propertyId
            //@AuthenticationPrincipal AppUser user ,
           ){
        Image image = imageService.uploadPropertyPhotos(file,bucketName,propertyId);
        return new ResponseEntity<>(image, HttpStatus.CREATED);

    }
}
