package webgejmikaback.com.programerika.service;

import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.exceptions.ScoreOutOfRangeException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerScoreServiceImpl implements PlayerScoreService {

    private final PlayerScoresRepository playerScoresRepository;

    public PlayerScoreServiceImpl(PlayerScoresRepository playerScoresRepository) {
        this.playerScoresRepository = playerScoresRepository;
    }

    @Override
    public PlayerScoreDTO savePlayerScore(PlayerScore playerScore) throws UsernameAlreadyExistsException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(playerScore.getUsername());
        if (optional.isPresent()) {
            throw new UsernameAlreadyExistsException("Username Already Exists in the Repository");
        } else {
            PlayerScore ps = playerScoresRepository.save(playerScore);
            return new PlayerScoreDTO(ps.getUid());
        }
    }

    @Override
    public void addPlayerScore(String username, Integer score) throws UsernameNotFoundException,ScoreOutOfRangeException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(username);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("Username Not Found in the Repository");
        }
        if (score <= 21 && score > 0) {
            optional.ifPresent(p -> p.setScore(optional.get().getScore() + score));
            optional.ifPresent(playerScoresRepository::save);
        } else {
            throw new ScoreOutOfRangeException("Player score is out of range!");
        }
    }

    @Override
    public List<PlayerScore> getTopScore() {
        return playerScoresRepository.getTopScore();
    }

    @Override
    public PlayerScore getByUsername(String username) throws UsernameNotFoundException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(username);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("Username Not Found in the Repository");
        }else {
            return optional.get();
        }
    }

    @Override
    public void delete(String uid) throws UsernameNotFoundException {
        if (!playerScoresRepository.existsById(uid)) {
            throw new UsernameNotFoundException("Username Not Found in the Repository");
        }else {
            playerScoresRepository.deleteById(uid);
        }
    }
}
