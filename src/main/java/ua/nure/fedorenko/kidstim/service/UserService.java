package ua.nure.fedorenko.kidstim.service;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public interface UserService {
    /**
     * gets user avatar from filename
     *
     * @param fileName name of the file to get image
     * @return buffered image which matches passed filename
     */
    BufferedImage getImage(String fileName)throws FileNotFoundException;
}
