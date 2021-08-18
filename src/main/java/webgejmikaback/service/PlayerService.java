package webgejmikaback.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import webgejmikaback.Model.PlayerScore;
import webgejmikaback.Repository.PlayerRepository;

import java.util.*;

@Service
public class PlayerService {


    private final PlayerRepository playerRepository;
    private final MongoTemplate mongoTemplate;

    public PlayerService(PlayerRepository playerRepository, MongoTemplate mongoTemplate) {
        this.playerRepository = playerRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public String saveAllScores() {
        PlayerScore ps = new PlayerScore("Nata", 21);
        PlayerScore ps1 = new PlayerScore("Pera", 15);
        PlayerScore ps2 = new PlayerScore("Bane", 21);
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

    public List<PlayerScore> getTopTenScores(){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC,"score")).limit(10);
        return mongoTemplate.find(query,PlayerScore.class);
        //return playerRepository.findAll().stream().sorted(Comparator.comparingInt(PlayerScore::getScore)).limit(10).collect(Collectors.toList());
    }

}
