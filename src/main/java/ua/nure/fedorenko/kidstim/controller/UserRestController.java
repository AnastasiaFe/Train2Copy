package ua.nure.fedorenko.kidstim.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.fedorenko.kidstim.AppConstants;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.ParentService;
import ua.nure.fedorenko.kidstim.service.UserService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@RestController
public class UserRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private ParentService parentService;
    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    @Value("${app.parentImagesFolder}")
    private String parentImagesFolder;

    @Value("${app.childrenImagesFolder}")
    private String childrenImagesFolder;

    @PutMapping(value = "/updateParent")
    @ResponseStatus(HttpStatus.OK)
    public void updateParent(@RequestBody Parent parent) {
        parentService.updateParent(parent);
    }

    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void uploadImage(@RequestParam MultipartFile file, @RequestParam("name") String name, @RequestParam("role") String role) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String rootPath = role.equals(AppConstants.CHILD) ? childrenImagesFolder : parentImagesFolder;
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                try (BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile))) {
                    stream.write(bytes);
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }

    }

    @GetMapping(value = "/getImage")
    public void getImage(@RequestParam("name") String name, @RequestParam("role") String role, HttpServletResponse response) throws IOException {
        String rootPath = role.equals(AppConstants.CHILD) ? childrenImagesFolder : parentImagesFolder;
        response.setContentType("image/png");
        try {
            OutputStream out = response.getOutputStream();
            BufferedImage avatar = userService.getImage(rootPath + "\\" + name);
            if (avatar != null) {
                ImageIO.write(avatar, AppConstants.IMAGES_EXTENSION, out);
            }
            out.close();
        } catch (IOException ex) {
            response.sendError(404);
        }
    }

    @PutMapping(value = "/updateChild")
    @ResponseStatus(HttpStatus.OK)
    public void updateChild(@RequestBody Child child) {
        childService.updateChild(child);
    }

    @PostMapping(value = "/addChild")
    @ResponseStatus(HttpStatus.CREATED)
    public void addChild(@RequestBody Child child, @RequestParam("id") String parentId) {
        childService.addChild(child, parentId);
    }

    @GetMapping(value = "/parent")
    public Parent getParentById(@RequestParam("id") String id) {
        Parent parent = parentService.getParentById(id);
        parent.setChildren(parentService.getParentsChildren(parent.getEmail()));
        return parent;
    }

    @GetMapping(value = "/child")
    public Child getChildById(@RequestParam("id") String id) {
        return childService.getChildById(id);
    }

    @GetMapping(value = "/parentByEmail")
    public Parent getParentByEmail(@RequestParam("email") String email) {
        return parentService.getParentByEmail(email);
    }

    @GetMapping(value = "/childrenByParent")
    public List<Child> getChildrenByParent(@RequestParam("email") String id) {
        return parentService.getParentsChildren(id);
    }

    @GetMapping(value = "/childByEmail")
    public Child getChildByEmail(@RequestParam("email") String id) {
        return childService.getChildByEmail(id);
    }
}
