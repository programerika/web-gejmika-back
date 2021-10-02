package webgejmikaback.com.programerika.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.converter.PlayerScoresConverter;
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

    public String delete(String id) {
        playerScoresRepository.deleteById(id);
        return "Score is deleted";
    }

    public Optional<PlayerScore> getPlayerByUsername(String id) {
        return playerScoresRepository.findById(id);
    }

//    public void saveScore(PlayerScore playerScore) {
//        Optional<PlayerScore> optionalPlayer = playerScoresRepository.findById(playerScore.getUsername());
//        if (optionalPlayer.isPresent()) {
//            optionalPlayer.ifPresent(p -> p.setUsername(playerScore.getUsername()));
//            optionalPlayer.ifPresent(p -> p.setScore(p.getScore() + playerScore.getScore()));
//            optionalPlayer.ifPresent(playerScoresRepository::save);
//        } else {
//            // proveriti sa vladom da li zeli da se upisuju igraci bez imena u bazu
//            try {
//                if (playerScore.getUsername().isBlank()) {
//                    throw new NullPointerException("Username cannot be empty!");
//                }
//                playerScoresRepository.save(playerScore);
//            } catch (NullPointerException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
    public ResponseEntity<?> saveScore(PlayerScore playerScore) {
        Optional<PlayerScore> playerScore1 = playerScoresRepository.findById(playerScore.getUsername());
    }

    public List<PlayerScore> getTopScore(){
        return playerScoresRepository.getTopScore();
    }

}
