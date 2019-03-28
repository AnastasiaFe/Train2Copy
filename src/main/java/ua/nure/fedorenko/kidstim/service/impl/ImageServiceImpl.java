package ua.nure.fedorenko.kidstim.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.fedorenko.kidstim.service.ImageService;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file, String fileName) {
        Map options = ObjectUtils.asMap(
                "transformation",
                new Transformation().width(400).height(300).crop("limit").fetchFormat("png"));
        String url = "";
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            url = (String) uploadResult.get("url");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return url;
    }
}
