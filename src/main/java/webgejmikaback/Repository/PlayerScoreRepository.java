package webgejmikaback.Repository;

import webgejmikaback.Model.PlayerScore;

import java.util.List;
import java.util.Optional;

public interface PlayerScoreRepository {

    List<PlayerScore> getTopTenScores();
}
