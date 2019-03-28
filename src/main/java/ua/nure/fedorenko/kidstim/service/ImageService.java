package ua.nure.fedorenko.kidstim.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile file, String fileName);
}
