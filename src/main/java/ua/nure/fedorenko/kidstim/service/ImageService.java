package ua.nure.fedorenko.kidstim.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public interface ImageService {

    String uploadImage(MultipartFile file, String fileName);

    BufferedImage getImage(String fileName) throws FileNotFoundException;
}
