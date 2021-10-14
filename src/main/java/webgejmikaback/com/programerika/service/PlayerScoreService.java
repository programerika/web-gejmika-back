package webgejmikaback.com.programerika.service;

import webgejmikaback.com.programerika.exceptions.ScoreOutOfRangeException;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.List;

public interface PlayerScoreService {

    PlayerScore savePlayerScore(PlayerScore playerScore) throws UsernameAlreadyExistsException, ScoreOutOfRangeException;
    void addPlayerScore(String username, Integer score) throws UsernameNotFoundException, ScoreOutOfRangeException;
    List<PlayerScore> getTopScore();
    PlayerScore getByUsername(String username) throws UsernameNotFoundException;
    void delete(String uid) throws UsernameNotFoundException;

}
