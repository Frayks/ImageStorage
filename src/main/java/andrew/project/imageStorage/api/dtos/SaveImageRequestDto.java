package andrew.project.imageStorage.api.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SaveImageRequestDto {
    private byte[] bytes;
    private String type;

}
