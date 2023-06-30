package com.eLearn.eLearning.app.service;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import com.eLearn.eLearning.app.entity.Upload;
import com.eLearn.eLearning.app.repository.UploadRepository;
import com.eLearn.eLearning.app.response.OpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class UploadService {
    @Autowired
    private UploadRepository uploadRepo;

    private final Cloudinary cloudinary = Singleton.getCloudinary();

    public OpResponse uploadImage(MultipartFile file, String name) throws IOException {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String urlLink = uploadResult.get("url").toString();
                Optional<Upload> uploadData = uploadRepo.findByName(name);
                if (uploadData.isEmpty()) {
                    Upload upload = new Upload();
                    upload.setName(name);
                    upload.setUrl(urlLink);
                    uploadRepo.save(upload);
                    return OpResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Image saved in DB")
                            .build();
                }
                uploadData.get().setUrl(uploadData.get().getUrl());
                uploadRepo.save(uploadData.get());
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Image saved in DB")
                        .build();
            }
                catch(Exception ex){
                    throw new RuntimeException("Error occurred");
                }

    }

    public OpResponse uploadVideo(MultipartFile file, String name) throws IOException {
        try {
            Map uploadResult = cloudinary.uploader().uploadLarge(file.getBytes(), ObjectUtils.asMap("resource_type", "video"));
            String urlLink = uploadResult.get("url").toString();
            Optional<Upload> uploadData = uploadRepo.findByName(name);
            if (uploadData.isEmpty()) {
                Upload upload = new Upload();
                upload.setName(name);
                upload.setUrl(urlLink);
                uploadRepo.save(upload);
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Video saved in DB")
                        .build();
            }
            uploadData.get().setUrl(uploadData.get().getUrl());
            uploadRepo.save(uploadData.get());
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Video saved in DB")
                    .build();
        }catch(Exception ex){
            throw new RuntimeException("Please add files less than 20MB");
        }
    }

}
