package com.travelbnb.travelbnb.controller;


import com.travelbnb.travelbnb.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/bucket")
public class  BucketController {

    private BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    //http://localhost:8080/api/v1/bucket/upload/file/tavelbnb01

    @PostMapping(path = "/upload/file/{bucketName}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam MultipartFile file,
             @PathVariable String bucketName
    ) throws IOException {
        return new ResponseEntity<>(bucketService.uploadFile(file,bucketName), HttpStatus.OK);
    }
}
