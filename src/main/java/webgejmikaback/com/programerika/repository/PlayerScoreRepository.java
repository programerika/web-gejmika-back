package webgejmikaback.com.programerika.repository;

import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;

import java.util.List;

public interface PlayerScoreRepository {

    List<PlayerScore> getTopTen();
    List<PlayerScore> getAll();
    PlayerScoreDTO getRankedScore(String username);
    List<PlayerScoreDTO> getTopTenRanked();
    List<PlayerScoreDTO> getAllRanked();
}
