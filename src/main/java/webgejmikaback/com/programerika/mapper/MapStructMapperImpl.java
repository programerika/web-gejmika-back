package webgejmikaback.com.programerika.mapper;

import org.springframework.stereotype.Component;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.HashMap;
import java.util.Map;


@Component
public class MapStructMapperImpl implements MapStructMapper {
    @Override
    public PlayerScore dtoToPlayerScore(PlayerScoreDTO playerScoreDTO, String gameId) {

        PlayerScore playerScore = new PlayerScore();
        playerScore.setUsername(playerScoreDTO.getUsername());

        // This if statement has been added as support purpose for version 1.0.0 of web-gejmika project
        if (gameId.equals("gejmika")){
            playerScore.setScore(playerScoreDTO.getScore());
        }

        Map<String,Integer>  map = new HashMap<>();
        map.put(gameId, playerScoreDTO.getScore());
        playerScore.setScores(map);

        return playerScore;
    }

    @Override
    public PlayerScoreDTO playerScoreToDTO(PlayerScore playerScore, String gameId) {

        PlayerScoreDTO playerScoreDTO = new PlayerScoreDTO();
        playerScoreDTO.setUsername(playerScore.getUsername());
        Integer scoreOfGivenGame = playerScore.getScores().get(gameId);
        playerScoreDTO.setScore(scoreOfGivenGame);

        return playerScoreDTO;

    }
}
