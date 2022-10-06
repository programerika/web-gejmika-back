package webgejmikaback.com.programerika.service;

import org.springframework.stereotype.Service;
import webgejmikaback.com.programerika.configmodels.AllAvailableGamesAndTheirLimits;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.*;
import webgejmikaback.com.programerika.mapper.PlayerScoreConverter;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerScoreServiceImpl implements PlayerScoreService {

    private final AllAvailableGamesAndTheirLimits allAvailableGamesAndTheirLimits;
    private final PlayerScoreConverter playerScoreConverter;
    private final PlayerScoresRepository playerScoresRepository;

    public PlayerScoreServiceImpl(PlayerScoresRepository playerScoresRepository, AllAvailableGamesAndTheirLimits allAvailableGamesAndTheirLimits, PlayerScoreConverter playerScoreConverter) {
        this.playerScoresRepository = playerScoresRepository;
        this.allAvailableGamesAndTheirLimits = allAvailableGamesAndTheirLimits;
        this.playerScoreConverter = playerScoreConverter;
    }

    @Override
    public PlayerScore savePlayerScore(PlayerScoreDTO playerScoreDTO, String gameId) throws UsernameAlreadyExistsException, ScoreOutOfRangeException, ProvidedGameNotExistsException {

        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(playerScoreDTO.getUsername());
        if (optional.isPresent()) {
            throw new UsernameAlreadyExistsException("Username Already Exists in the Repository or input is not correct");
        }

        if (isScoreInRange(playerScoreDTO.getScore(), gameId)) {
            throw new ScoreOutOfRangeException("Score is out of range");
        }

        if(checkIfGivenGameExists(gameId)){
            throw new ProvidedGameNotExistsException("Provided game not exist");
        }

        PlayerScore playerScore = playerScoreConverter.dtoToPlayerScore(playerScoreDTO,gameId);
        return playerScoresRepository.save(playerScore);
    }

    @Override
    public void addPlayerScore(String username, Integer score, String gameId) throws UsernameNotFoundException,ScoreOutOfRangeException, ProvidedGameNotExistsException{

        Optional<PlayerScore> optional = Optional.ofNullable(playerScoresRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username Not Found in the Repository")));

        if(isScoreInRange(score, gameId)){
            throw new ScoreOutOfRangeException("Score is out of range");
        }

        if(checkIfGivenGameExists(gameId)){
            throw new ProvidedGameNotExistsException("Provided game not exists");
        }
        PlayerScore p = optional.get();

        if (p.getScores().containsKey(gameId)){
            // This if statement has been added as support purpose for version 1.0.0 of web-gejmika project
            if(gameId.equals("gejmika")){
                p.setScore(p.getScore() + score);
                p.getScores().put(gameId,p.getScores().get(gameId) + score);
                playerScoresRepository.save(p);
            }else{
                p.getScores().put(gameId,p.getScores().get(gameId) + score);
                playerScoresRepository.save(p);
            }
        }else{
            // This if statement has been added as support purpose for version 1.0.0 of web-gejmika project
            if(gameId.equals("gejmika")) {
                p.setScore(score);
            }
            p.getScores().put(gameId,score);
            playerScoresRepository.save(p);
        }
    }

    @Override
    public List<PlayerScoreDTO> getTopScore(String gameId) throws ProvidedGameNotExistsException{
        if(checkIfGivenGameExists(gameId)){
            throw new ProvidedGameNotExistsException("Provided game not exists");
        }

        Integer limit = allAvailableGamesAndTheirLimits.getGames().get(gameId).getTopScorePlayersLimit();
        List<PlayerScore> playerScores = playerScoresRepository.getTopScore(gameId,limit);

        return playerScores.stream().map(p -> playerScoreConverter.playerScoreToDTO(p,gameId)).collect(Collectors.toList());
    }

    @Override
    public PlayerScoreDTO getByUsername(String username, String gameId) throws UsernameNotFoundException {
        Optional<PlayerScore> optional = playerScoresRepository.findByUsername(username);

        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("Username Not Found in the Repository");
        }

        PlayerScore p = optional.get();
        if(!p.getScores().containsKey(gameId)){
            throw new UserDoesNotHaveScoreForProvidedGameException("User hasn't played provided game yet");
        }

        return playerScoreConverter.playerScoreToDTO(p,gameId);

    }

    @Override
    public void delete(String uid) throws UidNotFoundException {
        if (!playerScoresRepository.existsById(uid)) {
            throw new UidNotFoundException("Uid Not Found in the Repository");
        }else {
            playerScoresRepository.deleteById(uid);
        }
    }

    private boolean isScoreInRange(int score, String gameId) {
        Integer minScoreOfCurrentGame = allAvailableGamesAndTheirLimits.getGames().get(gameId).getMinScore();
        Integer maxScoreOfCurrentGame = allAvailableGamesAndTheirLimits.getGames().get(gameId).getMaxScore();

        return (score > maxScoreOfCurrentGame || score < minScoreOfCurrentGame);
    }

    private boolean checkIfGivenGameExists(String game){
        return !allAvailableGamesAndTheirLimits.getGames().containsKey(game);
    }

}
