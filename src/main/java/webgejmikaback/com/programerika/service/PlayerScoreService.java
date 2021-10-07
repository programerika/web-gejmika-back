package webgejmikaback.com.programerika.service;

import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.List;

public interface PlayerScoreService {

    PlayerScoreDTO savePlayerScore(PlayerScore playerScore) throws UsernameAlreadyExistsException;
    void addPlayerScore(String username, Integer score) throws UsernameNotFoundException;
    List<PlayerScore> getTopScore();
    PlayerScore getByUsername(String username) throws UsernameNotFoundException;
    void delete(String uid) throws UsernameNotFoundException;

}
