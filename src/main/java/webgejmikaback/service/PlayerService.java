package webgejmikaback.service;

import org.springframework.stereotype.Service;
import webgejmikaback.Model.PlayerScore;
import webgejmikaback.Repository.PlayerRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String saveAllScores() {
        PlayerScore ps = new PlayerScore("Nata", Arrays.asList(13,8,13,13,21,13,21));
        PlayerScore ps1 = new PlayerScore("Pera", Arrays.asList(13,8,8,13,0,21));
        PlayerScore ps2 = new PlayerScore("Bane", Arrays.asList(13,8,13,13,21,8,21,21));
        playerRepository.saveAll(Arrays.asList(ps,ps1,ps2));
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

    // need to be done again
    public Optional<PlayerScore> getTopTenById(String username) {
        Optional<PlayerScore> playerScore = playerRepository.findById(username);
        playerScore.ifPresent(ps -> reversedList(ps.getScore()));
        return playerScore;
    }

    private List<Integer> reversedList(List<Integer> list) {
        return list.stream().sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }
}
