package webgejmikaback.com.programerika.mapper;

import org.mapstruct.Mapper;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.model.PlayerScore;

@Mapper
public interface MapStructMapper {

    PlayerScore dtoToPlayerScore(PlayerScoreDTO playerScoreDTO, String gameId);
    PlayerScoreDTO playerScoreToDTO(PlayerScore playerScore, String gameId);

}
