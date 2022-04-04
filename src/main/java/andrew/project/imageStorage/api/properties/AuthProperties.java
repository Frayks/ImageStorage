package andrew.project.imageStorage.api.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("classpath:auth.properties")
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    private String username;
    private String password;

}
