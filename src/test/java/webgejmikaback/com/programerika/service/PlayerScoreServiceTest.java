package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import webgejmikaback.com.programerika.exceptions.ScoreOutOfRangeException;
import webgejmikaback.com.programerika.exceptions.UidNotFoundException;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class PlayerScoreServiceTest {

    @Mock
    private PlayerScoresRepository repository;

    @Mock
    private PlayerScoreService serviceUnderTest;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(PlayerScoresRepository.class);
        serviceUnderTest = new PlayerScoreServiceImpl(repository);
    }

    @Test
    @DisplayName("Test getByUsername should return valid output")
    public void testGetByUsernameShouldReturnValidOutput() {
        // given
        PlayerScore ps = new PlayerScore("","bole55",13);
        // when
        Mockito.when(repository.findByUsername(ps.getUsername()))
                .thenReturn(Optional.of(ps));
        PlayerScore expected = serviceUnderTest.getByUsername(ps.getUsername());
        // then
        assertNotNull(expected);
        assertEquals("bole55",expected.getUsername());
        Mockito.verify(repository,Mockito.times(1)).findByUsername("bole55");
    }

    @Test
    @DisplayName("Test getByUsername throws an exception for username is blank or not exists")
    public void testGetByUsernameShouldThrowAnException_For_UsernameIsBlankOrNotExists() {
        // when
        Mockito.when(repository.findByUsername(Mockito.anyString()))
                .thenThrow(new UsernameNotFoundException("Username cannot be empty or not exists"));
        // then
        assertThrows(UsernameNotFoundException.class, () ->
                serviceUnderTest.getByUsername(""));
    }

    @Test
    @DisplayName("Test savePlayerScore should create new PlayerScore")
    void testSavePlayerScoreShouldCreateNewPlayerScore() {
        // given new PlayerScore("testUID","bole55",anyInt())
        PlayerScore ps = new PlayerScore("testUID","bole55",anyInt());
        // when
        Mockito.lenient().when(repository.findByUsername(ps.getUsername()))
                .thenReturn(Optional.empty());
        Mockito.lenient().when(repository.save(ps)).thenReturn(ps);

        PlayerScore saved = serviceUnderTest.savePlayerScore(ps);
        // then
//        assertEquals(saved.getUsername(),ps.getUsername());
        Mockito.verify((repository), Mockito.times(1)).save(ps);
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for PlayerScore username already exists")
    void testSavePlayerScoreShouldThrowAnException_For_PlayerScoreUsernameAlreadyExists() {
        // given
        PlayerScore ps = new PlayerScore("", "bole55", 13);
        //when
        Mockito.when(repository.findByUsername(ps.getUsername())).thenReturn(Optional.of(ps));
        // then
        assertThrows(UsernameAlreadyExistsException.class, () ->
                serviceUnderTest.savePlayerScore(ps));
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for score out of range")
    void testSavePlayerScoreShouldThrowAnException_For_ScoreOutOfRange() {
        // given
        PlayerScore ps = new PlayerScore("", "bole55", 22);
        // when
        Mockito.lenient().when(repository.save(ps)).thenThrow(ScoreOutOfRangeException.class);
        // then
        assertThrows(ScoreOutOfRangeException.class, () ->
            serviceUnderTest.savePlayerScore(ps)
        );
    }

    @Test
    @DisplayName("Test getTopScore should return top score list")
    void testGetTopScoreShouldReturnTopScoreList() {
        // given
        PlayerScore ps1 = new PlayerScore("", "bole55", 13);
        PlayerScore ps2 = new PlayerScore("", "udzej11", 8);
        PlayerScore ps3 = new PlayerScore("", "naki12", 21);
        List<PlayerScore> list = Arrays.asList(ps1,ps2,ps3);
        repository.saveAll(list);
        // when
        Mockito.when(repository.getTopScore()).thenReturn(list);
        List<PlayerScore> expected = serviceUnderTest.getTopScore();
        // then
        assertNotNull(list);
        assertEquals(list.get(0).getScore(), expected.get(0).getScore());
        Mockito.verify((repository),Mockito.times(1)).getTopScore();
    }

    @Test
    @DisplayName("Test addPlayerScore should throw an exception for username not found")
    void testAddPlayerScoreShouldThrowAnException_For_UsernameNotFound() {
        // when
        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        // then
        assertThrows(UsernameNotFoundException.class, () ->
            serviceUnderTest.addPlayerScore("zika33",13)
        );
    }

    @Test
    @DisplayName("Test addPlayerScore success")
    void testAddPlayerScoreShouldSuccess() {
        // given
        PlayerScore ps = new PlayerScore("", "Nadja12", 13);
        // when
        Mockito.when(repository.findByUsername(ps.getUsername())).thenReturn(Optional.of(ps));
        Mockito.lenient().when(repository.save(ps)).thenReturn(ps);
        serviceUnderTest.addPlayerScore(ps.getUsername(), anyInt());
        // then
        Mockito.verify((repository), Mockito.times(1)).save(ps);

    }

    @Test
    @DisplayName("Test delete should delete PlayerScore by UID")
    void testDeleteShouldDeletePlayerScoreByUID() {
        // given
        PlayerScore ps = new PlayerScore("615e009a1e947e29fc97038a", "Coyote12", 13);
        // when
        Mockito.when(repository.existsById(Mockito.anyString())).thenReturn(true);
        try {
            serviceUnderTest.delete(ps.getUid());
        } catch (UidNotFoundException e) {
            e.printStackTrace();
        }
        // then
        assertNotNull(ps.getUid());
        assertEquals("615e009a1e947e29fc97038a",ps.getUid());
        Mockito.verify((repository), Mockito.times(1)).deleteById(ps.getUid());
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
}