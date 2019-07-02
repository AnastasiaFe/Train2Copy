package ua.nure.fedorenko.kidstim.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.fedorenko.kidstim.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileSystemImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemImageServiceImpl.class);
    @Value("${app.imagesFolder}")
    private String imagesFolder;

    @Override
    public String uploadImage(MultipartFile file, String fileName) {
        String uploadedFileName = "";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File dir = new File(imagesFolder);
                if (!dir.exists())
                    dir.mkdirs();
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + fileName);
                try (BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile))) {
                    stream.write(bytes);
                }
                uploadedFileName = serverFile.getAbsolutePath();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return uploadedFileName;
    }


    @Override
    public BufferedImage getImage(String fileName) throws FileNotFoundException {
        File avatar = new File(fileName);
        BufferedImage image;
        try {
            image = ImageIO.read(avatar);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new FileNotFoundException("Image with such name is not found!");
        }
        return image;
    }

}
