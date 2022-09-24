package webgejmikaback.com.programerika.configmodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cfg-games")
public class AllAvailableGamesAndTheirLimits {

    private Map<String, GameScoreLimit> games;

}
