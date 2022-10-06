package webgejmikaback.com.programerika.configmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "games")
@AllArgsConstructor
@NoArgsConstructor
public class GameScoreLimit {

    private Integer minScore;
    private Integer maxScore;
    private Integer topScorePlayersLimit;

}
