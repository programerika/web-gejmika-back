package webgejmikaback.com.programerika.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webgejmikaback.com.programerika.model.PlayerScore;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerScore,String>, PlayerScoreRepository {

}
