package webgejmikaback.com.programerika.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.configmodels.AllAvailableGamesAndTheirLimits;
import webgejmikaback.com.programerika.exceptions.UidNotFoundException;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.exceptions.ScoreOutOfRangeException;
import webgejmikaback.com.programerika.mapper.MapStructMapper;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerScoreServiceImpl implements PlayerScoreService {

    @Value("${config-params.min-score}")
    private int minScore;

    @Value("${config-params.max-score}")
    private int maxScore;


    private final AllAvailableGamesAndTheirLimits allAvailableGamesAndTheirLimits;

    private final MapStructMapper mapStructMapper;
    private final PlayerScoresRepository playerScoresRepository;

    public PlayerScoreServiceImpl(PlayerScoresRepository playerScoresRepository, AllAvailableGamesAndTheirLimits allAvailableGamesAndTheirLimits, MapStructMapper mapStructMapper) {
        this.playerScoresRepository = playerScoresRepository;
        this.allAvailableGamesAndTheirLimits = allAvailableGamesAndTheirLimits;
        this.mapStructMapper= mapStructMapper;
    }

    @Override
    public PlayerScore savePlayerScore(PlayerScore playerScore) throws UsernameAlreadyExistsException, ScoreOutOfRangeException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(playerScore.getUsername());
        if (optional.isPresent()) {
            throw new UsernameAlreadyExistsException("Username Already Exists in the Repository or input is not correct");
        } else {
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
            throw new ScoreOutOfRangeException("Score is out of range");
        }
    }

    @Override
    public List<PlayerScore> getTopScore() {

        PlayerScore playerScore = new PlayerScore();
        playerScore.setUsername("Baki12");
        playerScore.setScore(21);
        Map<String,Integer> map = new HashMap<>();
        map.put("gejmika",21);
        playerScore.setScores(map);

        mapStructMapper.playerScoreToDTO(playerScore, "gejmika");

        System.out.println(allAvailableGamesAndTheirLimits.getGames().keySet());
        System.out.println(allAvailableGamesAndTheirLimits.getGames().values());
        allAvailableGamesAndTheirLimits.getGames().values().forEach(t -> System.out.println(t.getMinScore()));

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
    public void delete(String uid) throws UidNotFoundException {
        if (!playerScoresRepository.existsById(uid)) {
            throw new UidNotFoundException("Uid Not Found in the Repository");
        }else {
            playerScoresRepository.deleteById(uid);
        }
    }

    private boolean isScoreInRange(int score) {
        return (score <= maxScore && score >= minScore);
    }
}
