package webgejmikaback.com.programerika.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.converter.PlayerScoresConverter;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.net.URI;
import java.util.*;

@Service
public class PlayerScoresService {

    private final PlayerScoresRepository playerScoresRepository;
    private final PlayerScoresConverter playerScoresConverter;

    public PlayerScoresService(PlayerScoresRepository playerScoresRepository, PlayerScoresConverter playerScoresConverter) {
        this.playerScoresRepository = playerScoresRepository;
        this.playerScoresConverter = playerScoresConverter;
    }

    public String delete(String id) {
        playerScoresRepository.deleteById(id);
        return "Score is deleted";
    }

    public Optional<PlayerScore> getPlayerByUsername(String id) {
        return playerScoresRepository.findById(id);
    }

    public PlayerScoreDTO savePlayerScore(PlayerScore playerScore) {
        if (playerScore.getUsername() != null) {
            playerScoresRepository.save(playerScore);
        }
        PlayerScore ps = getByUsername(playerScore.getUsername());
        return new PlayerScoresConverter().modelToDTO(ps);
    }

    public List<PlayerScore> getTopScore(){
        return playerScoresRepository.getTopScore();
    }

    private PlayerScore getByUsername(String username) {
        return playerScoresRepository.findByUsername(username);
    }

}
