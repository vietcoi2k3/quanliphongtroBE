package com.apec.pos.service;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadService {

    @Autowired
    private Cloudinary cloudinary;
    public String uploadFile( byte[] bytes) throws IOException {
        return cloudinary.uploader()
                .upload(bytes,
                        Map.of("public_id", UUID.randomUUID().toString(),
                                "resource_type", "auto"))
                .get("url")
                .toString();
    }
}