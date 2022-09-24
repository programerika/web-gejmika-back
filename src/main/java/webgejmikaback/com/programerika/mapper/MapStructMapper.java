package webgejmikaback.com.programerika.mapper;

import org.mapstruct.Mapper;
import webgejmikaback.com.programerika.dto.PlayerScorePostDTO;
import webgejmikaback.com.programerika.model.PlayerScore;

import javax.validation.constraints.Max;

@Mapper
public interface MapStructMapper {

    PlayerScore dtoToPlayerScore(PlayerScorePostDTO playerScorePostDTO, String gameId);
    PlayerScorePostDTO playerScoreToDTO(PlayerScore playerScore, String gameId);

}
