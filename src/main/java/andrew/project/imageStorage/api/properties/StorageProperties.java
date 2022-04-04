package andrew.project.imageStorage.api.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("classpath:storage.properties")
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String path;

}
