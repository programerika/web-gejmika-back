package webgejmikaback.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webgejmikaback.Model.Player;

@Repository
public interface PlayerRepository extends MongoRepository<Player,String>, PlayerScoreRepository {

}
