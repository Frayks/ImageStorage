package andrew.project.imageStorage.api.libraries;

import andrew.project.imageStorage.api.dtos.SaveImageRequestDto;
import andrew.project.imageStorage.api.dtos.SaveImageResponseDto;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MainLib {

    byte[] getImage(String name) throws IOException;

    SaveImageResponseDto saveImage(SaveImageRequestDto request) throws IOException;

    void deleteImage(String name) throws FileNotFoundException;

}
