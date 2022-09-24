package webgejmikaback.com.programerika.mapper;

import org.springframework.stereotype.Component;
import webgejmikaback.com.programerika.dto.PlayerScorePostDTO;
import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
public class MapStructMapperImpl implements MapStructMapper {
    @Override
    public PlayerScore dtoToPlayerScore(PlayerScorePostDTO playerScorePostDTO, String gameId) {

        if (playerScorePostDTO == null) {
            return null;
        }

        PlayerScore playerScore = new PlayerScore();
        playerScore.setUsername(playerScorePostDTO.getUsername());
        playerScore.setScore(playerScorePostDTO.getScore());
        Map<String,Integer>  map = new HashMap<>();
        map.put(playerScorePostDTO.getUsername(), playerScore.getScore());
        playerScore.setScores(map);

        return playerScore;
    }

    @Override
    public PlayerScorePostDTO playerScoreToDTO(PlayerScore playerScore, String gameId) {

        PlayerScorePostDTO playerScorePostDTO = new PlayerScorePostDTO();
        playerScorePostDTO.setUsername(playerScore.getUsername());
        Integer scoreOfGivenGame = playerScore.getScores().get(gameId);
        playerScorePostDTO.setScore(scoreOfGivenGame);

        return playerScorePostDTO;

    }
}
