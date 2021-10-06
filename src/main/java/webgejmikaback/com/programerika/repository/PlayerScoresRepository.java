package webgejmikaback.com.programerika.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webgejmikaback.com.programerika.model.PlayerScore;

import java.util.Optional;

@Repository
public interface PlayerScoresRepository extends MongoRepository<PlayerScore,String>, PlayerScoreRepositoryTopScore {
    Optional<PlayerScore> findByUsername(String username);
    boolean existsByUsername(String username);
}
