package andrew.project.imageStorage.libraries;

import andrew.project.imageStorage.api.dtos.SaveImageRequestDto;
import andrew.project.imageStorage.api.dtos.SaveImageResponseDto;
import andrew.project.imageStorage.api.libraries.MainLib;
import andrew.project.imageStorage.api.properties.StorageProperties;
import andrew.project.imageStorage.api.utils.GeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class MainLibImpl implements MainLib {

    private static final int FILENAMES_LENGTH = 64;

    private StorageProperties storageProperties;

    @Override
    public byte[] getImage(String name) throws IOException {
        String fileName = Paths.get(name).getFileName().toString();
        Path filePath = Paths.get(storageProperties.getPath(), fileName);
        if (!filePath.toFile().exists()) {
            throw new FileNotFoundException(String.format("File not found:%s", name));
        }
        return Files.readAllBytes(filePath);
    }

    @Override
    public SaveImageResponseDto saveImage(SaveImageRequestDto requestDto) throws IOException {
        String fileName = GeneratorUtil.genRandStr(FILENAMES_LENGTH) + "." + requestDto.getType();
        Path filePath = Paths.get(storageProperties.getPath(), fileName);
        Files.write(filePath, requestDto.getBytes());
        SaveImageResponseDto responseDto = new SaveImageResponseDto();
        responseDto.setName(fileName);
        return responseDto;
    }

    @Override
    public void deleteImage(String name) throws FileNotFoundException {
        File file = Paths.get(storageProperties.getPath(), name).toFile();
        if (!file.delete()) {
            throw new FileNotFoundException(String.format("File not found:%s", name));
        }
    }

    @Autowired
    public void setStorageProperties(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

}
