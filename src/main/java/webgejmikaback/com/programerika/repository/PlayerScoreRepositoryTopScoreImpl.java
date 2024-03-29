package webgejmikaback.com.programerika.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.List;

@Repository
public class PlayerScoreRepositoryTopScoreImpl implements PlayerScoreRepositoryTopScore {
    private final MongoTemplate mongoTemplate;

    public PlayerScoreRepositoryTopScoreImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<PlayerScore> getTopScore(String gameId, Integer limit){
        Query query = new Query();

        query.with(Sort.by(Sort.Direction.DESC,"scores."+gameId))
                .addCriteria(Criteria.where("scores."+gameId).ne(null))
                    .limit(limit);

        return mongoTemplate.find(query, PlayerScore.class);
    }

}
