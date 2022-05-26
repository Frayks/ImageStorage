package andrew.project.imageStorage.controllers;

import andrew.project.imageStorage.api.dtos.SaveImageRequestDto;
import andrew.project.imageStorage.api.dtos.SaveImageResponseDto;
import andrew.project.imageStorage.api.libraries.MainLib;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    private MainLib mainLib;

    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImage(HttpServletRequest request, @RequestParam() String name) {
        LOGGER.debug(String.format("%s -> getImage: name=%s", request.getRemoteAddr(), name));
        byte[] imageBytes = mainLib.getImage(name);
        if (imageBytes == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(imageBytes);
        }
    }

    @PostMapping("/saveImage")
    public ResponseEntity<SaveImageResponseDto> saveImage(HttpServletRequest request, @RequestBody SaveImageRequestDto requestDto) {
        LOGGER.debug(String.format("%s -> saveImage: type=%s", request.getRemoteAddr(), requestDto.getType()));
        SaveImageResponseDto responseDto = mainLib.saveImage(requestDto);
        if (responseDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @GetMapping("/deleteImage")
    public ResponseEntity<Void> deleteImage(HttpServletRequest request, @RequestParam String name) {
        LOGGER.debug(String.format("%s -> deleteImage: name=%s", request.getRemoteAddr(), name));
        boolean deleted = mainLib.deleteImage(name);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/deleteImageList")
    public ResponseEntity<Void> deleteImageList(HttpServletRequest request, @RequestParam List<String> nameList) {
        LOGGER.debug(String.format("%s -> deleteImageList: nameList=%s", request.getRemoteAddr(), nameList.toString()));
        boolean allDeleted = mainLib.deleteImageList(nameList);
        if (allDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Autowired
    public void setMainLib(MainLib mainLib) {
        this.mainLib = mainLib;
    }

}
