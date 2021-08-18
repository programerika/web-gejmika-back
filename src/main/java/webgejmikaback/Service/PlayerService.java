package webgejmikaback.Service;

import org.springframework.stereotype.Service;
import webgejmikaback.Model.PlayerScore;
import webgejmikaback.Repository.PlayerRepository;

import java.util.*;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String saveAllScores() {
        PlayerScore ps = new PlayerScore("Nata", 21);
        PlayerScore ps1 = new PlayerScore("Pera", 15);
        PlayerScore ps2 = new PlayerScore("Bane", 21);
        PlayerScore ps3 = new PlayerScore("Milan", 21);
        PlayerScore ps4 = new PlayerScore("Joca", 11);
        PlayerScore ps5 = new PlayerScore("Djole", 8);
        PlayerScore ps6 = new PlayerScore("Muja", 5);
        PlayerScore ps7 = new PlayerScore("Siki", 13);
        PlayerScore ps8 = new PlayerScore("Slave", 8);
        PlayerScore ps9 = new PlayerScore("Anci", 21);
        PlayerScore ps10 = new PlayerScore("Mica", 18);
        PlayerScore ps11 = new PlayerScore("Lana", 21);
        PlayerScore ps12 = new PlayerScore("Steva", 1);
        PlayerScore ps13 = new PlayerScore("Shone", 8);
        PlayerScore ps14 = new PlayerScore("Mile", 11);
        PlayerScore ps15 = new PlayerScore("Hana", 21);
        playerRepository.saveAll(Arrays.asList(ps,ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9,ps10,ps11,ps12,ps13,ps14,ps15));
        return "All Scores have been successfully saved";
    }

    public String deleteAllScores() {
        playerRepository.deleteAll();
        return "All scores have been deleted";
    }

    public List<PlayerScore> getAllScores() {
        return playerRepository.findAll();
    }

    public Optional<PlayerScore> getPlayerByUserName(String id) {
        return playerRepository.findById(id);
    }

    public PlayerScore saveScore(PlayerScore playerScore) {
       if (playerScore.getUsername() != null) {
           return playerRepository.save(playerScore);
       }
       throw new RuntimeException("Player score cannot be null");
    }

    public Optional<PlayerScore> updateScore(PlayerScore newPlayerScore) {
        Optional<PlayerScore> playerScore = playerRepository.findById(newPlayerScore.getUsername());
        if (newPlayerScore.getUsername() != null) {
            playerScore.ifPresent(p -> p.setUsername(newPlayerScore.getUsername()));
            playerScore.ifPresent(p -> p.setScore(p.getScore() + newPlayerScore.getScore()));
            playerScore.ifPresent(playerRepository::save);

            return playerScore;
        }
        throw new RuntimeException("Player score cannot be null");
    }

    public List<PlayerScore> getTopTenScores(){
        return playerRepository.getTopTenScores();
    }

}
