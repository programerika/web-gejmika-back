package webgejmikaback.Repository;

import webgejmikaback.Model.Player;

import java.util.List;

public interface PlayerScoreRepository {

    List<Player> getTopTen();
    List<Player> getAll();
}
