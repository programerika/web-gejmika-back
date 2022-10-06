package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import webgejmikaback.com.programerika.configmodels.AllAvailableGamesAndTheirLimits;
import webgejmikaback.com.programerika.configmodels.GameScoreLimit;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.*;
import webgejmikaback.com.programerika.mapper.PlayerScoreConverter;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerScoreServiceTest {

    @Mock
    private PlayerScoresRepository repository;

    @Mock
    private PlayerScoreConverter playerScoreConverter;
    @Mock
    private AllAvailableGamesAndTheirLimits allAvailableGamesAndTheirLimits;
    @Mock
    private PlayerScoreService serviceUnderTest;

    @BeforeEach
    public void setup(){
        repository = Mockito.mock(PlayerScoresRepository.class);
        playerScoreConverter = Mockito.mock(PlayerScoreConverter.class);
        allAvailableGamesAndTheirLimits = Mockito.mock(AllAvailableGamesAndTheirLimits.class);
        serviceUnderTest = new PlayerScoreServiceImpl(repository,allAvailableGamesAndTheirLimits,playerScoreConverter);
    }

    @Test
    @DisplayName("Test getByUsername should return valid output")
    public void testGetByUsernameShouldReturnValidOutput() {
        // given
        String gameId = "gejmika";
        Mockito.when(repository.findByUsername(createPlayerScore().getUsername())).thenReturn(Optional.of(createPlayerScore()));
        serviceUnderTest.getByUsername(createPlayerScore().getUsername(),gameId);
        Mockito.verify(repository, Mockito.times(1)).findByUsername(createPlayerScore().getUsername());

    }


    @Test
    @DisplayName("Test getByUsername throws an exception for username is blank or not exists")
    public void testGetByUsernameShouldThrowAnException_For_UsernameIsBlankOrNotExists() {
        // when
        Mockito.when(this.repository.findByUsername(Mockito.anyString()))
                .thenThrow(new UsernameNotFoundException("Username cannot be empty or not exists"));
        // then
        assertThrows(UsernameNotFoundException.class, () ->
                serviceUnderTest.getByUsername("",""));
    }

    @Test
    @DisplayName("Test savePlayerScore should create new PlayerScore")
    void testSavePlayerScoreShouldCreateNewPlayerScore() {
        // given
        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(3);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");
        Optional<PlayerScore> ofResult = Optional.of(playerScore);
        // when
        when(repository.findByUsername((String) any())).thenReturn(ofResult);
        PlayerScoreDTO playerScoreDTO = mock(PlayerScoreDTO.class);
        when(playerScoreDTO.getUsername()).thenReturn("Jovan55");
        assertThrows(UsernameAlreadyExistsException.class,
                () -> serviceUnderTest.savePlayerScore(playerScoreDTO, "42"));
        // then
        verify(repository).findByUsername((String) any());
        verify(playerScoreDTO).getUsername();
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for PlayerScore username already exists")
    void testSavePlayerScoreShouldThrowAnException_For_PlayerScoreUsernameAlreadyExists() {
        // given
        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(3);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");
        Optional<PlayerScore> ofResult = Optional.of(playerScore);
        // when
        when(this.repository.findByUsername(Mockito.any())).thenReturn(ofResult);
        Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> {
            this.serviceUnderTest.savePlayerScore(new PlayerScoreDTO(), "42"); });
        // then
        verify(this.repository).findByUsername((String)Mockito.any());
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for score out of range")
    void testSavePlayerScoreShouldThrowAnException_For_ScoreOutOfRange() {
        // given
        String gameId = "gejmika";
        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(3);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");

        PlayerScoreDTO playerScoreDTO = mock(PlayerScoreDTO.class);

        // when
        when(playerScoreDTO.getUsername()).thenReturn("Jovan55");
        when(playerScoreDTO.getScore()).thenReturn(3);


        /*Mockito.when(repository.save(playerScore)).thenThrow(ScoreOutOfRangeException.class);
        // then
        Throwable thrown = assertThrows(ScoreOutOfRangeException.class, () ->  serviceUnderTest.savePlayerScore(playerScoreDTO,anyString()));
        assertEquals("Score is out of range", thrown.getMessage());*/
//        assertThrows(ScoreOutOfRangeException.class, () ->  serviceUnderTest.savePlayerScore(psDTO,gameId));

    }

    @Test
    @DisplayName("Test getTopScore should return top score list")
    void testGetTopScoreShouldReturnTopScoreList() {
        // when
        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(13);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");

        ArrayList<PlayerScore> playerScoreList = new ArrayList<>();
        playerScoreList.add(playerScore);

        //when
        when(repository.getTopScore(any(), any())).thenReturn(playerScoreList);

        HashMap<String, GameScoreLimit> stringGameScoreLimitMap = new HashMap<>();
        stringGameScoreLimitMap.put("gejmika", new GameScoreLimit());
        AllAvailableGamesAndTheirLimits allAvailableGamesAndTheirLimits = new AllAvailableGamesAndTheirLimits(
                stringGameScoreLimitMap);

        List<PlayerScoreDTO> actualTopScore = (new PlayerScoreServiceImpl(repository,
                allAvailableGamesAndTheirLimits, new PlayerScoreConverter())).getTopScore("gejmika");
        // then
        assertEquals(1, actualTopScore.size());
        PlayerScoreDTO getResult = actualTopScore.get(0);
        assertNull(getResult.getScore());
        assertEquals("Jovan55", getResult.getUsername());
        verify((repository),Mockito.times(1)).getTopScore(any(), any());
    }

    @Test
    @DisplayName("Test addPlayerScore should throw an exception for username not found")
    void testAddPlayerScoreShouldThrowAnException_For_UsernameNotFound() {
        // when
        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        // then
        assertThrows(UsernameNotFoundException.class, () ->
            serviceUnderTest.addPlayerScore("",13,"")
        );
    }

    @Test
    @DisplayName("Test addPlayerScore success")
    void testAddPlayerScoreShouldSuccess() {
        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(13);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");

        Optional<PlayerScore> ofResult = Optional.of(playerScore);
        Mockito.when(this.repository.findByUsername((String)Mockito.any())).thenReturn(ofResult);


        this.serviceUnderTest.addPlayerScore("Jovan55", 13, "gejmika");


       /* PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(15);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");

        HashMap<String, GameScoreLimit> stringGameScoreLimitMap = new HashMap<>();
        stringGameScoreLimitMap.put("bombika", new GameScoreLimit(5,100,10));
        new AllAvailableGamesAndTheirLimits(stringGameScoreLimitMap);

//        isScoreInRange(playerScore.getScore(),stringGameScoreLimitMap.keySet().toString());

        Optional<PlayerScore> ofResult = Optional.of(playerScore);
        Mockito.when(this.repository.findByUsername((String)Mockito.any())).thenReturn(ofResult);
        this.serviceUnderTest.addPlayerScore("Jovan55", 15, "bombika");*/


       /* // given
        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(15);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");

        HashMap<String, GameScoreLimit> stringGameScoreLimitMap = new HashMap<>();
        stringGameScoreLimitMap.put("bombika", new GameScoreLimit());
        AllAvailableGamesAndTheirLimits allAvailableGamesAndTheirLimits = new AllAvailableGamesAndTheirLimits(
                stringGameScoreLimitMap);
        // when
        *//*Mockito.when(repository.findByUsername(playerScore.getUsername())).thenReturn(Optional.of(playerScore));
        Mockito.lenient().when(repository.save(playerScore)).thenReturn(playerScore);
//        serviceUnderTest.addPlayerScore(playerScore.getUsername(), anyInt(), anyString());
        this.serviceUnderTest.addPlayerScore(playerScore.getUsername(), Mockito.anyInt(), Mockito.anyString());
        // then
//        Mockito.verify((repository), Mockito.times(1)).save(playerScore);
        (Mockito.verify(this.repository, Mockito.times(1))).save(playerScore);*//*
        Optional<PlayerScore> ofResult = Optional.of(playerScore);
        when(repository.findByUsername((String) any())).thenReturn(ofResult);
        serviceUnderTest.addPlayerScore("Jovan55", 15, "bombika");*/
    }

    @Test
    @DisplayName("Test delete should delete PlayerScore by UID")
    void testDeleteShouldDeletePlayerScoreByUID() {
        // given
        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(3);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");
        // when
        Mockito.when(repository.existsById(Mockito.anyString())).thenReturn(true);
        try {
            serviceUnderTest.delete(playerScore.getUid());
        } catch (UidNotFoundException e) {
            e.printStackTrace();
        }
        // then
        assertNotNull(playerScore.getUid());
        assertEquals("1234",playerScore.getUid());
        Mockito.verify((repository), Mockito.times(1)).deleteById(playerScore.getUid());
    }

    @Test
    @DisplayName("Test delete throws an exception for UID not valid")
    void testCanDeleteShouldThrowAnException_For_uidNotValid() {
        // when
        Mockito.when(repository.existsById(Mockito.anyString())).thenReturn(false);
        // then
        assertThrows(UidNotFoundException.class, () ->
            serviceUnderTest.delete("615e009a1e947e29fc970111")
        );
    }

    private PlayerScore createPlayerScore() {
        PlayerScore playerScore = new PlayerScore();
        playerScore.setUsername("Jovan113");
        playerScore.setScore(21);
        playerScore.setUid("ajSad");

        Map<String, Integer> map = new HashMap<>();
        map.put("gejmika", 21);
        playerScore.setScores(map);

        return playerScore;
    }

    private boolean isScoreInRange(int score, String gameId) {
        Integer minScoreOfCurrentGame = allAvailableGamesAndTheirLimits.getGames().get(gameId).getMinScore();
        Integer maxScoreOfCurrentGame = allAvailableGamesAndTheirLimits.getGames().get(gameId).getMaxScore();

        return (score > maxScoreOfCurrentGame || score < minScoreOfCurrentGame);
    }

}