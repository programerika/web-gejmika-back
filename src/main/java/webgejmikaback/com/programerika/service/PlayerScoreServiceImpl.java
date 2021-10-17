package webgejmikaback.com.programerika.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.exceptions.ScoreOutOfRangeException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerScoreServiceImpl implements PlayerScoreService {

    @Value("${config-params.min-score}")
    private int minScore;

    @Value("${config-params.max-score}")
    private int maxScore;

    private final PlayerScoresRepository playerScoresRepository;

    public PlayerScoreServiceImpl(PlayerScoresRepository playerScoresRepository) {
        this.playerScoresRepository = playerScoresRepository;
    }

    @Override
    public PlayerScore savePlayerScore(PlayerScore playerScore) throws UsernameAlreadyExistsException, ScoreOutOfRangeException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(playerScore.getUsername());
        if (optional.isPresent()) {
            throw new UsernameAlreadyExistsException("Username Already Exists in the Repository or input is not correct");
        } else {
            if (playerScore.getUsername() == null) {
                throw new IllegalArgumentException("Input is not valid");
            }
            if (!isScoreInRange(playerScore.getScore())) {
                throw new ScoreOutOfRangeException("Score is out of range");
            }
            else return playerScoresRepository.save(playerScore);
        }
    }

    @Override
    public void addPlayerScore(String username, Integer score) throws UsernameNotFoundException,ScoreOutOfRangeException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Username Not Found in the Repository");
        }
        PlayerScore p = optional.get();
        if (isScoreInRange(score)) {
            p.setScore(p.getScore() + score);
            playerScoresRepository.save(p);
        } else {
            throw new ScoreOutOfRangeException("Player score is out of range");
        }
    }

    @Override
    public List<PlayerScore> getTopScore() {
        return playerScoresRepository.getTopScore();
    }

    @Override
    public PlayerScore getByUsername(String username) throws UsernameNotFoundException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Username Not Found in the Repository");
        }else {
            return optional.get();
        }
    }

    @Override
    public void delete(String uid) throws UsernameNotFoundException {
        if (!playerScoresRepository.existsById(uid)) {
            throw new UsernameNotFoundException("Username Not Found with uid: "+ uid +" in the Repository");
        }else {
            playerScoresRepository.deleteById(uid);
        }
    }

    private boolean isScoreInRange(int score) {
        return (score <= maxScore && score >= minScore);
    }
}
