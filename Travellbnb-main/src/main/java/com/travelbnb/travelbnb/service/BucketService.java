package com.travelbnb.travelbnb.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BucketService {
    String uploadFile(MultipartFile file, String bucketName) throws IOException;
}
