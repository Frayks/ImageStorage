package andrew.project.imageStorage.libraries;

import andrew.project.imageStorage.api.dtos.SaveImageRequestDto;
import andrew.project.imageStorage.api.dtos.SaveImageResponseDto;
import andrew.project.imageStorage.api.libraries.MainLib;
import andrew.project.imageStorage.api.properties.StorageProperties;
import andrew.project.imageStorage.api.utils.GeneratorUtil;
import andrew.project.imageStorage.controllers.MainController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class MainLibImpl implements MainLib {

    private static final Logger LOGGER = LogManager.getLogger(MainLibImpl.class);

    private static final int FILENAMES_LENGTH = 64;

    private StorageProperties storageProperties;

    @Override
    public byte[] getImage(String name) {
        try {
            String fileName = Paths.get(name).getFileName().toString();
            Path filePath = Paths.get(storageProperties.getPath(), fileName);
            if (!filePath.toFile().exists()) {
                throw new FileNotFoundException(String.format("File not found:%s", name));
            }
            return Files.readAllBytes(filePath);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public SaveImageResponseDto saveImage(SaveImageRequestDto requestDto) {
        try {
            String fileName = GeneratorUtil.genRandStr(FILENAMES_LENGTH) + "." + requestDto.getType();
            Path filePath = Paths.get(storageProperties.getPath(), fileName);
            Files.write(filePath, requestDto.getBytes());
            SaveImageResponseDto responseDto = new SaveImageResponseDto();
            responseDto.setName(fileName);
            return responseDto;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public boolean deleteImage(String name) {
        try {
            File file = Paths.get(storageProperties.getPath(), name).toFile();
            if (!file.delete()) {
                throw new FileNotFoundException(String.format("File not found:%s", name));
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean deleteImageList(List<String> nameList) {
        boolean allDeleted = true;
        for (String name : nameList) {
            try {
                File file = Paths.get(storageProperties.getPath(), name).toFile();
                file.delete();
            } catch (Exception e) {
                LOGGER.error(e);
                allDeleted = false;
            }
        }
        return allDeleted;
    }

    @Autowired
    public void setStorageProperties(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

}
