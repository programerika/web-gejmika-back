package webgejmikaback.com.programerika.configmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config-params")
@AllArgsConstructor
@NoArgsConstructor
public class AllAvailableGamesAndTheirLimits {

    private Map<String, GameScoreLimit> games;

}
