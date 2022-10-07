package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import webgejmikaback.com.programerika.configmodels.AllAvailableGamesAndTheirLimits;
import webgejmikaback.com.programerika.configmodels.GameScoreLimit;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.*;
import webgejmikaback.com.programerika.mapper.PlayerScoreConverter;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PlayerScoreServiceTest {

    private PlayerScoresRepository repository;
    private PlayerScoreConverter playerScoreConverter;
    private AllAvailableGamesAndTheirLimits allAvailableGamesAndTheirLimits;
    private PlayerScoreService serviceUnderTest;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(PlayerScoresRepository.class);
        playerScoreConverter = Mockito.mock(PlayerScoreConverter.class);
        allAvailableGamesAndTheirLimits = Mockito.mock(AllAvailableGamesAndTheirLimits.class);
        serviceUnderTest = new PlayerScoreServiceImpl(repository, allAvailableGamesAndTheirLimits, playerScoreConverter);
    }

    @Test
    @DisplayName("Test getByUsername should return valid output")
    public void testGetByUsernameShouldReturnValidOutput() {
        // given
        String gameId = "gejmika";
        // when
        when(repository.findByUsername(createPlayerScore().getUsername())).thenReturn(Optional.of(createPlayerScore()));

        serviceUnderTest.getByUsername(createPlayerScore().getUsername(), gameId);
        // then
        verify(repository, times(1)).findByUsername(createPlayerScore().getUsername());
    }

    @Test
    @DisplayName("Test getByUsername should throw UserDoesNotHaveScoreForProvidedGameException")
    public void testGetByUsernameShouldThrowUserDoesNotHaveScoreForProvidedGameException() {
        // given
        String gameId = "nepostojika";
        // when
        when(repository.findByUsername(createPlayerScore().getUsername())).thenReturn(Optional.of(createPlayerScore()));
        // then
        assertThrows(UserDoesNotHaveScoreForProvidedGameException.class, () ->
                serviceUnderTest.getByUsername(createPlayerScore().getUsername(), gameId));
        verify(repository, times(1)).findByUsername(createPlayerScore().getUsername());
    }

    @Test
    @DisplayName("Test getByUsername throws an exception for username is blank or not exists")
    public void testGetByUsernameShouldThrowAnException_For_UsernameIsBlankOrNotExists() {
        // when
        Mockito.when(this.repository.findByUsername(Mockito.anyString()))
                .thenThrow(new UsernameNotFoundException("Username cannot be empty or not exists"));
        // then
        assertThrows(UsernameNotFoundException.class, () ->
                serviceUnderTest.getByUsername("", ""));
    }

    @Test
    @DisplayName("Test savePlayerScore should create new PlayerScore")
    void testSavePlayerScoreShouldCreateNewPlayerScore() {
        // given
        String gameId = "gejmika";
        PlayerScore playerScore = createPlayerScore();
        Optional<PlayerScore> ofResult = Optional.of(playerScore);
        // when
        when(repository.findByUsername(anyString())).thenReturn(ofResult);
        PlayerScoreDTO playerScoreDTO = mock(PlayerScoreDTO.class);
        when(playerScoreDTO.getUsername()).thenReturn("Jovan55");
        assertThrows(UsernameAlreadyExistsException.class,
                () -> serviceUnderTest.savePlayerScore(playerScoreDTO, gameId));
        // then
        verify(repository).findByUsername(anyString());
        verify(playerScoreDTO).getUsername();
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for PlayerScore username already exists")
    void testSavePlayerScoreShouldThrowAnException_For_PlayerScoreUsernameAlreadyExists() {
        // given
        String gameId = "gejmika";
        PlayerScore playerScore = createPlayerScore();
        Optional<PlayerScore> ofResult = Optional.of(playerScore);
        // when
        when(this.repository.findByUsername(Mockito.any())).thenReturn(ofResult);
        Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> this.serviceUnderTest.savePlayerScore(new PlayerScoreDTO(), gameId));
        // then
        verify(this.repository).findByUsername(Mockito.any());
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for score out of range")
    void testSavePlayerScoreShouldThrowAnException_For_ScoreOutOfRange() {
        // given
        String gameId = "gejmika";
        PlayerScoreDTO playerScoreDTO = createPlayerScoreDTOWithInvalidRange();
        // when
        when(repository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(allAvailableGamesAndTheirLimits.getGames()).thenReturn(createGameScoreLimitMap());
        // then
        assertThrows(ScoreOutOfRangeException.class, () ->
                serviceUnderTest.savePlayerScore(playerScoreDTO, gameId));
    }

    @Test
    @DisplayName("Test getTopScore should return top score list")
    void testGetTopScoreShouldReturnTopScoreList() {
        // given
        String gameId = "gejmika";
        List<PlayerScore> myList = Collections.singletonList(createPlayerScore());
        PlayerScoreDTO playerScoreDTO = createPlayerScoreDTO();
        // when
        when(allAvailableGamesAndTheirLimits.getGames()).thenReturn(createGameScoreLimitMap());
        when(repository.getTopScore(anyString(), anyInt())).thenReturn(myList);
        when(playerScoreConverter.playerScoreToDTO(any(PlayerScore.class), anyString())).thenReturn(playerScoreDTO);

        serviceUnderTest.getTopScore(gameId);
        // then
        verify(allAvailableGamesAndTheirLimits, times(2)).getGames();
        verify(repository, times(1)).getTopScore(anyString(), anyInt());
        verify(playerScoreConverter, times(1)).playerScoreToDTO(any(PlayerScore.class), anyString());
    }

    @Test
    @DisplayName("Test addPlayerScore should throw an exception for username not found")
    void testAddPlayerScoreShouldThrowAnException_For_UsernameNotFound() {
        // when
        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        // then
        assertThrows(UsernameNotFoundException.class, () ->
                serviceUnderTest.addPlayerScore("", 13, "")
        );
    }

    @Test
    @DisplayName("Test addPlayerScore success")
    void testAddPlayerScoreShouldSuccess() {
        // given
        String gameId = "gejmika";
        PlayerScore playerScore = createPlayerScore();
        Optional<PlayerScore> ofResult = Optional.of(playerScore);
        // when
        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(ofResult);
        Mockito.when(allAvailableGamesAndTheirLimits.getGames()).thenReturn(createGameScoreLimitMap());

        serviceUnderTest.addPlayerScore(playerScore.getUsername(), playerScore.getScores().get(gameId), gameId);
        // then
        verify(repository,times(1)).findByUsername(anyString());
        verify(allAvailableGamesAndTheirLimits,times(2)).getGames();
    }

    @Test
    @DisplayName("Test delete should delete PlayerScore by UID")
    void testDeleteShouldDeletePlayerScoreByUID() {
        // given
        PlayerScore playerScore = createPlayerScore();
        // when
        Mockito.when(repository.existsById(Mockito.anyString())).thenReturn(true);
        try {
            serviceUnderTest.delete(playerScore.getUid());
        } catch (UidNotFoundException e) {
            e.printStackTrace();
        }
        // then
        assertNotNull(playerScore.getUid());
        assertEquals("1234", playerScore.getUid());
        Mockito.verify((repository), Mockito.times(1)).deleteById(playerScore.getUid());
    }

    @Test
    @DisplayName("Test delete throws an exception for UID not valid")
    void testCanDeleteShouldThrowAnException_For_uidNotValid() {
        // given
        String uid = "615e009a1e947e29fc970111";
        // when
        Mockito.when(repository.existsById(Mockito.anyString())).thenReturn(false);
        // then
        assertThrows(UidNotFoundException.class, () ->
                serviceUnderTest.delete(uid)
        );
    }

    private PlayerScore createPlayerScore() {
        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(21);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");

        Map<String, Integer> map = new HashMap<>();
        map.put("gejmika", 21);
        playerScore.setScores(map);

        return playerScore;
    }

    private PlayerScoreDTO createPlayerScoreDTO() {
        PlayerScoreDTO playerScoreDTO = new PlayerScoreDTO();
        playerScoreDTO.setUsername("jovan55");
        playerScoreDTO.setScore(21);
        return playerScoreDTO;
    }

    private PlayerScoreDTO createPlayerScoreDTOWithInvalidRange() {
        PlayerScoreDTO playerScoreDTO = new PlayerScoreDTO();
        playerScoreDTO.setUsername("jovan55");
        playerScoreDTO.setScore(27);
        return playerScoreDTO;
    }

    private Map<String, GameScoreLimit> createGameScoreLimitMap() {
        Map<String, GameScoreLimit> gameLimitsMap = new HashMap<>();
        GameScoreLimit gameScoreLimit = new GameScoreLimit();
        gameScoreLimit.setTopScorePlayersLimit(10);
        gameScoreLimit.setMinScore(8);
        gameScoreLimit.setMaxScore(21);
        gameLimitsMap.put("gejmika", gameScoreLimit);
        return gameLimitsMap;
    }
}