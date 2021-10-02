package webgejmikaback.com.programerika.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.List;

@Repository
public class PlayerScoreRepositoryTopScoreImpl implements PlayerScoreRepositoryTopScore {

    private final MongoTemplate mongoTemplate;
    @Value("${top-scores-limit.number}")
    public int limit;

    public PlayerScoreRepositoryTopScoreImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<PlayerScore> getTopScore(){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC,"score")).limit(limit);
        return mongoTemplate.find(query, PlayerScore.class);
    }

}
