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

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    private MainLib mainLib;

    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImage(@RequestParam() String name) {
        LOGGER.info(String.format("getImage: name=%s", name));
        try {
            byte[] imageBytes = mainLib.getImage(name);
            return ResponseEntity.ok(imageBytes);
        } catch (IOException e) {
            LOGGER.debug(e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/saveImage")
    public ResponseEntity<SaveImageResponseDto> saveImage(@RequestBody SaveImageRequestDto requestDto) {
        LOGGER.info("saveImage:");
        try {
            SaveImageResponseDto responseDto = mainLib.saveImage(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            LOGGER.debug(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/deleteImage")
    public ResponseEntity<Void> deleteImage(@RequestParam String name) {
        LOGGER.info(String.format("deleteImage: name=%s", name));
        try {
            mainLib.deleteImage(name);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.debug(e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Autowired
    public void setMainLib(MainLib mainLib) {
        this.mainLib = mainLib;
    }

}
