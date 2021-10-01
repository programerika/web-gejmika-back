package webgejmikaback.com.programerika.service;

import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.repository.PlayerRepository;

import java.util.*;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String delete(String id) {
        playerRepository.deleteById(id);
        return "Score is deleted";
    }

    public Optional<PlayerScore> getPlayerByUsername(String id) {
        return playerRepository.findById(id);
    }

    public void saveScore(PlayerScore playerScore) {
        Optional<PlayerScore> optionalPlayer = playerRepository.findById(playerScore.getUsername());
        if (optionalPlayer.isPresent()) {
            optionalPlayer.ifPresent(p -> p.setUsername(playerScore.getUsername()));
            optionalPlayer.ifPresent(p -> p.setScore(p.getScore() + playerScore.getScore()));
            optionalPlayer.ifPresent(playerRepository::save);
        } else {
            // proveriti sa vladom da li zeli da se upisuju igraci bez imena u bazu
            try {
                if (playerScore.getUsername().isBlank()) {
                    throw new NullPointerException("Username cannot be empty!");
                }
                playerRepository.save(playerScore);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<PlayerScoreDTO> getTopTenRanked() {
        return playerRepository.getTopTenRanked();
    }

    public PlayerScoreDTO getRankedScore(String username) {
        return playerRepository.getRankedScore(username);
    }

    public List<PlayerScoreDTO> getTopTen(String username){
        PlayerScoreDTO player = playerRepository.getRankedScore(username);
        List<PlayerScoreDTO> playerScoreDTOList;
        if (player.getPlaceNo() <= 10) {
            playerScoreDTOList = playerRepository.getTopTenRanked();
        }
        else {
            PlayerScoreDTO playerScoreDTO = playerRepository.getRankedScore(username);
            playerScoreDTOList = playerRepository.getTopTenRanked();
            playerScoreDTOList.add(playerScoreDTO);
        }
        return playerScoreDTOList;
    }

}
