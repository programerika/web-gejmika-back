package webgejmikaback.com.programerika.service;

import com.mongodb.DuplicateKeyException;
import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.converter.PlayerScoresConverter;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.*;

@Service
public class PlayerScoresService {

    private final PlayerScoresRepository playerScoresRepository;
    private final PlayerScoresConverter playerScoresConverter;

    public PlayerScoresService(PlayerScoresRepository playerScoresRepository, PlayerScoresConverter playerScoresConverter) {
        this.playerScoresRepository = playerScoresRepository;
        this.playerScoresConverter = playerScoresConverter;
    }

    public void delete(String uid) {
        playerScoresRepository.deleteById(uid);
    }

    public Optional<PlayerScore> getPlayerByUsername(String username) {
        return playerScoresRepository.findByUsername(username);
    }

    public PlayerScoreDTO savePlayerScore(PlayerScore playerScore) {
        Optional<PlayerScore> optional = getPlayerByUsername(playerScore.getUsername());
        if (!optional.isPresent()) {
            playerScoresRepository.save(playerScore);
            PlayerScore ps = getByUsername(playerScore.getUsername());
            return playerScoresConverter.modelToDTO(ps);
        } else {
            return null;
        }
    }

    public void addPlayerScore(String username, Integer score) {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(username);
        System.out.println(optional.get().getUsername());
        if (score <= 21) {
            optional.ifPresent(p -> p.setScore(optional.get().getScore() + score));
            optional.ifPresent(p -> playerScoresRepository.save(p));
        }else {
            System.out.println("");
        }
    }

    public List<PlayerScore> getTopScore(){
        return playerScoresRepository.getTopScore();
    }

    public PlayerScore getByUsername(String username) {
        return playerScoresRepository.findByUsername(username).get();
    }


}
