package webgejmikaback.com.programerika.configmodels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "games")
public class GameScoreLimit {

    private Integer minScore;
    private Integer maxScore;
    private Integer topScorePlayersLimit;

}
