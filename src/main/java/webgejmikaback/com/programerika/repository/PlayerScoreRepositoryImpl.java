package webgejmikaback.com.programerika.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerScoreRepositoryImpl implements PlayerScoreRepository {

    private final MongoTemplate mongoTemplate;

    public PlayerScoreRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<PlayerScore> getTopTen(){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC,"score")).limit(10);
        return mongoTemplate.find(query, PlayerScore.class);
    }

    @Override
    public List<PlayerScore> getAll() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC,"score"));
        return mongoTemplate.find(query, PlayerScore.class);
    }

    @Override
    public PlayerScoreDTO getRankedScore(String username) {
        PlayerScore playerScore = mongoTemplate.findById(username,PlayerScore.class);
        int rank = getRank(username);
        PlayerScoreDTO playerScoreDTO = new PlayerScoreDTO();
        if (playerScore != null) {
            playerScoreDTO.setUsername(playerScore.getUsername());
            playerScoreDTO.setScore(playerScore.getScore());
            playerScoreDTO.setPlaceNo(rank);
        }
        return playerScoreDTO;
    }

    @Override
    public List<PlayerScoreDTO> getTopTenRanked() {
        List<PlayerScore> playerList = getTopTen();
        List<PlayerScoreDTO> playerScoreDTOList = new ArrayList<>();
        if (playerList.size() > 0) {
            playerList.forEach(p -> {
                PlayerScoreDTO playerScoreDTO = new PlayerScoreDTO();
                playerScoreDTO.setUsername(p.getUsername());
                playerScoreDTO.setScore(p.getScore());
                playerScoreDTO.setPlaceNo(getRank(p.getUsername()));

                playerScoreDTOList.add(playerScoreDTO);
            });
        }
        return playerScoreDTOList;
    }

    @Override
    public List<PlayerScoreDTO> getAllRanked() {
        List<PlayerScore> playerList = getAll();
        List<PlayerScoreDTO> allRankedList = new ArrayList<>();
        if (playerList.size() > 0) {
            playerList.forEach(p -> {
                PlayerScoreDTO playerScoreDTO = new PlayerScoreDTO();
                playerScoreDTO.setUsername(p.getUsername());
                playerScoreDTO.setScore(p.getScore());
                playerScoreDTO.setPlaceNo(getRank(p.getUsername()));

                allRankedList.add(playerScoreDTO);
            });
        }
        return allRankedList;
    }

    private int getRank(String username) {
        PlayerScore playerScore = mongoTemplate.findById(username,PlayerScore.class);
        Query query = new Query();
        query.addCriteria(Criteria.where("score").gt(playerScore.getScore()));
//        Criteria criteria = Criteria.where("score").gte(player.getScore());
        return ((int) mongoTemplate.count(query, PlayerScore.class)+1);
    }
}
