package webgejmikaback.com.programerika.service;

import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.UidNotFoundException;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.List;

public interface PlayerScoreService {

    PlayerScore savePlayerScore(PlayerScoreDTO playerScoreDTO, String gameId) throws UsernameAlreadyExistsException;

    void addPlayerScore(String username, Integer score,String gameId) throws UsernameNotFoundException;

    List<PlayerScoreDTO> getTopScore(String gameId);

    PlayerScoreDTO getByUsername(String username, String gameId) throws UsernameNotFoundException;

    void delete(String uid) throws UidNotFoundException;

}
