package webgejmikaback.com.programerika.service;

import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.PlayerAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.PlayerNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.List;

public interface PlayerService {

    PlayerScoreDTO savePlayerScore(PlayerScore playerScore) throws PlayerAlreadyExistsException;
    void addPlayerScore(String username, Integer score) throws PlayerNotFoundException;
    List<PlayerScore> getTopScore();
    PlayerScore getByUsername(String username) throws PlayerNotFoundException;
    void delete(String uid) throws PlayerNotFoundException;

}
