package webgejmikaback.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import webgejmikaback.Model.Player;

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
}
