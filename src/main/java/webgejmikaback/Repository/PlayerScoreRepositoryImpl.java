package webgejmikaback.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import webgejmikaback.Model.Player;
import webgejmikaback.Model.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerScoreRepositoryImpl implements PlayerScoreRepository {

    private final MongoTemplate mongoTemplate;

    public PlayerScoreRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Player> getTopTen(){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC,"score")).limit(10);
        return mongoTemplate.find(query, Player.class);
    }

    @Override
    public List<Player> getAll() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC,"score"));
        return mongoTemplate.find(query, Player.class);
    }

    @Override
    public PlayerDTO getRankedScore(String username) {
        Player player = mongoTemplate.findById(username,Player.class);
        int rank = getRank(username);
        PlayerDTO playerDTO = new PlayerDTO();
        if (player != null) {
            playerDTO.setUsername(player.getUsername());
            playerDTO.setScore(player.getScore());
            playerDTO.setPlaceNo(rank);
        }
        return playerDTO;
    }

    @Override
    public List<PlayerDTO> getTopTenRanked() {
        List<Player> playerList = getTopTen();
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        if (playerList.size() > 0) {
            playerList.forEach(p -> {
                PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.setUsername(p.getUsername());
                playerDTO.setScore(p.getScore());
                playerDTO.setPlaceNo(getRank(p.getUsername()));

                playerDTOList.add(playerDTO);
            });
        }
        return playerDTOList;
    }

    @Override
    public List<PlayerDTO> getAllRanked() {
        List<Player> playerList = getAll();
        List<PlayerDTO> allRankedList = new ArrayList<>();
        if (playerList.size() > 0) {
            playerList.forEach(p -> {
                PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.setUsername(p.getUsername());
                playerDTO.setScore(p.getScore());
                playerDTO.setPlaceNo(getRank(p.getUsername()));

                allRankedList.add(playerDTO);
            });
        }
        return allRankedList;
    }

    private int getRank(String username) {
        Player player = mongoTemplate.findById(username,Player.class);
        Query query = new Query();
        query.addCriteria(Criteria.where("score").gt(player.getScore()));
//        Criteria criteria = Criteria.where("score").gte(player.getScore());
        return ((int) mongoTemplate.count(query, Player.class)+1);
    }
}
