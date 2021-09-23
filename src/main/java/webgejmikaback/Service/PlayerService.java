package webgejmikaback.Service;

import org.springframework.stereotype.Service;
import webgejmikaback.Model.Player;
import webgejmikaback.Repository.PlayerRepository;

import java.util.*;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String saveAll() {
        Player ps = new Player("Nata97", 21);
        Player ps1 = new Player("Pera12", 15);
        Player ps2 = new Player("Bane76", 21);
        Player ps3 = new Player("Miki95", 21);
        Player ps4 = new Player("Joca22", 11);
        Player ps5 = new Player("Djol78", 8);
        Player ps6 = new Player("Muja44", 5);
        Player ps7 = new Player("Siki33", 13);
        Player ps8 = new Player("Slav32", 8);
        Player ps9 = new Player("Anci67", 21);
        Player ps10 = new Player("Mica89", 18);
        Player ps11 = new Player("Lana91", 21);
        Player ps12 = new Player("sale34", 1);
        Player ps13 = new Player("sava77", 8);
        Player ps14 = new Player("Mile89", 11);
        Player ps15 = new Player("Hana55", 21);
        Player ps16 = new Player("Baki78", 21);
        playerRepository.saveAll(Arrays.asList(ps,ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9,ps10,ps11,ps12,ps13,ps14,ps15,ps16));
        return "All Scores have been successfully saved";
    }

    public String deleteAll() {
        playerRepository.deleteAll();
        return "All scores deleted";
    }

    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerByUsername(String id) {
        return playerRepository.findById(id);
    }

    public void saveScore(Player player) {
        Optional<Player> optionalPlayer = playerRepository.findById(player.getUsername());
        if (optionalPlayer.isPresent()) {
            optionalPlayer.ifPresent(p -> p.setUsername(player.getUsername()));
            optionalPlayer.ifPresent(p -> p.setScore(p.getScore() + player.getScore()));
            optionalPlayer.ifPresent(playerRepository::save);
        } else {
            // proveriti sa vladom da li zeli da se upisuju igraci bez imena u bazu
            try {
                if (player.getUsername().isBlank()) {
                    throw new NullPointerException("Username cannot be empty!");
                }
                playerRepository.save(player);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
//    public Player saveScore(Player player) {
//       if (player.getUsername() != null) {
//           return playerRepository.save(player);
//       }
//       throw new RuntimeException("Player score cannot be null");
//    }
//
//    public Optional<Player> updateScore(Player newPlayerScore) {
//        Optional<Player> playerScore = playerRepository.findById(newPlayerScore.getUsername());
//        if (newPlayerScore.getUsername() != null) {
//            playerScore.ifPresent(p -> p.setUsername(newPlayerScore.getUsername()));
//            playerScore.ifPresent(p -> p.setScore(p.getScore() + newPlayerScore.getScore()));
//            playerScore.ifPresent(playerRepository::save);
//
//            return playerScore;
//        }
//        throw new RuntimeException("Player score cannot be null");
//    }

    public List<Player> getTopTen(){
        return playerRepository.getTopTen();
    }

}
