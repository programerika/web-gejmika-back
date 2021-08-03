package webgejmikaback.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webgejmikaback.Model.PlayerScore;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerScore,String> {

}
