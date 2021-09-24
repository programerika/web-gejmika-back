package webgejmikaback.Repository;

import webgejmikaback.Model.Player;
import webgejmikaback.Model.PlayerDTO;

import java.util.List;

public interface PlayerScoreRepository {

    List<Player> getTopTen();
    List<Player> getAll();
    PlayerDTO getRankedScore(String username);
    List<PlayerDTO> getTopTenRanked();
    List<PlayerDTO> getAllRanked();
}
