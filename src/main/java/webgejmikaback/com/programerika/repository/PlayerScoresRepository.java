package webgejmikaback.com.programerika.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webgejmikaback.com.programerika.model.PlayerScore;

import javax.validation.constraints.Pattern;

@Repository
public interface PlayerScoresRepository extends MongoRepository<PlayerScore,String>, PlayerScoreRepositoryTopScore {

}
