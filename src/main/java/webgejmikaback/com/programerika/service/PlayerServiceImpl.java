package webgejmikaback.com.programerika.service;

import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.converter.PlayerScoresConverter;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.PlayerAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.PlayerNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerScoresRepository playerScoresRepository;
    private final PlayerScoresConverter playerScoresConverter;

    public PlayerServiceImpl(PlayerScoresRepository playerScoresRepository, PlayerScoresConverter playerScoresConverter) {
        this.playerScoresRepository = playerScoresRepository;
        this.playerScoresConverter = playerScoresConverter;
    }

    @Override
    public PlayerScoreDTO savePlayerScore(PlayerScore playerScore) throws PlayerAlreadyExistsException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(playerScore.getUsername());
        if (optional.isPresent()) {
            throw new PlayerAlreadyExistsException("Username Already Exists in Repository");
        } else {
            PlayerScore ps = playerScoresRepository.save(playerScore);
            return playerScoresConverter.modelToDTO(ps);
        }
    }

    @Override
    public void addPlayerScore(String username, Integer score) throws PlayerNotFoundException,IllegalArgumentException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(username);
        if (!optional.isPresent()) {
            throw new PlayerNotFoundException("Username Not Found in Repository");
        }
        if (score <= 21 && score > 0) {
            optional.ifPresent(p -> p.setScore(optional.get().getScore() + score));
            optional.ifPresent(p -> playerScoresRepository.save(p));
        } else {
            throw new IllegalArgumentException("Player score is out of range!");
        }
    }

    @Override
    public List<PlayerScore> getTopScore() {
        return playerScoresRepository.getTopScore();
    }

    @Override
    public PlayerScore getByUsername(String username) throws PlayerNotFoundException {
        Optional<PlayerScore> user = playerScoresRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new PlayerNotFoundException("User Not Found in Repository");
        }else {
            return user.get();
        }
    }

    @Override
    public void delete(String uid) throws PlayerNotFoundException {
        if (!playerScoresRepository.existsById(uid)) {
            throw new PlayerNotFoundException("User Not Found in Repository");
        }else {
            playerScoresRepository.deleteById(uid);
        }
    }
}
