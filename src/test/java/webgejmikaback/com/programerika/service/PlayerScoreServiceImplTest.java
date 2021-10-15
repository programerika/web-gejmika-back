package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerScoreServiceImplTest {

    @Mock
    private PlayerScoresRepository repository;

    @InjectMocks
    private PlayerScoreServiceImpl service;

    @Test
    @DisplayName("Test getByUsername should return valid output")
    public void getByUsernameShouldReturnValidOutput() throws UsernameNotFoundException {
        // given
        PlayerScore ps = new PlayerScore("","bole55",13);
        // when
        when(repository.findByUsername(anyString()))
                .thenReturn(Optional.of(ps));

        PlayerScore expected = service.getByUsername(ps.getUsername());
        // then
        assertNotNull(expected);
        assertEquals("bole55",expected.getUsername());
        verify(repository).findByUsername("bole55");
    }

    @Test
    @DisplayName("Test getByUsername throws an exception for username is blank")
    public void getByUsernameShouldThrowAnException_For_UsernameIsBlank() throws UsernameNotFoundException {
        // given
        PlayerScore ps = new PlayerScore("","bole55",13);
        // then
        assertThrows(UsernameNotFoundException.class, () -> {
            service.getByUsername("");
        });
    }

    @Test
    @DisplayName("Test getByUsername throws an exception for username not exists")
    public void getByUsernameShouldThrowAnException_For_UsernameNotExists() throws UsernameNotFoundException {
        // given
        PlayerScore ps = new PlayerScore("","bole55",13);
        // then
        assertThrows(UsernameNotFoundException.class, () -> {
            service.getByUsername("test44");
        });
    }

    @Test
    @DisplayName("Test savePlayerScore should create new PlayerScore")
    void savePlayerScoreShouldCreateNewPlayerScore() throws UsernameAlreadyExistsException {
        // given
        PlayerScore ps = new PlayerScore("", "bole55", 13);
        // when
        assertThrows(UsernameNotFoundException.class, () -> {
            service.getByUsername("test44");
        });
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for PlayerScore username already exists")
    void savePlayerScoreShouldThrowAnException_For_PlayerScoreUsernameAlreadyExists() throws UsernameAlreadyExistsException {
        // given
        PlayerScore ps = new PlayerScore("", "bole55", 13);

        given(repository.findByUsername(ps.getUsername())).willReturn(Optional.of(ps));
        // then
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            service.savePlayerScore(ps);
        });
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for PlayerScore username is null")
    void savePlayerScoreShouldThrowAnException_For_PlayerScoreUsernameIsNull() throws UsernameAlreadyExistsException {
        // given
        PlayerScore ps = new PlayerScore("", null, 13);

        given(repository.findByUsername(ps.getUsername())).willThrow(IllegalArgumentException.class);
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            repository.findByUsername(ps.getUsername());
        });
    }

    @Test
    @DisplayName("Test getTopScore should return top score list")
    void getTopScoreShouldReturnTopScoreList() {
        // given
        PlayerScore ps1 = new PlayerScore("", "bole55", 13);
        PlayerScore ps2 = new PlayerScore("", "udzej11", 8);
        PlayerScore ps3 = new PlayerScore("", "naki12", 21);
        List<PlayerScore> list = Arrays.asList(ps1,ps2,ps3);
        repository.saveAll(list);
        // when
        when(repository.getTopScore()).thenReturn(list);
        List<PlayerScore> expected = service.getTopScore();
        // then
        assertNotNull(list);
        assertEquals(list,expected);
        verify(repository).getTopScore();
    }

    @Test
    @DisplayName("Test addPlayerScore should add score")
    void addPlayerScoreShouldAddScore() throws UsernameNotFoundException {
        // given
        PlayerScore ps = new PlayerScore("", "Nadja12", 13);
        Integer initScore = ps.getScore();
        Integer score = 21;
        // when
        when(repository.findByUsername(anyString()))
                .thenReturn(Optional.of(ps));

        service.addPlayerScore(ps.getUsername(),score);
        // then
        Integer addedScore = initScore + score;
        assertNotNull(ps.getUsername());
        assertNotNull(score);
        assertEquals("Nadja12",ps.getUsername());
        assertEquals(addedScore,initScore+score);
        verify(repository).save(ps);
    }

    @Test
    @DisplayName("Test delete should delete PlayerScore by UID")
    void deleteShouldDeletePlayerScoreByUID() throws UsernameNotFoundException {
        // given
        PlayerScore ps = new PlayerScore("615e009a1e947e29fc97038a", "Coyote12", 13);
        // when
        when(repository.existsById(anyString())).thenReturn(true);
        service.delete(ps.getUid());
        // then
        assertNotNull(ps.getUid());
        assertEquals("615e009a1e947e29fc97038a",ps.getUid());
        verify(repository).deleteById(ps.getUid());
    }

    @Test
    @DisplayName("Test delete throws an exception for UID not valid")
    void canDeletePlayerScoreByUID() throws UsernameNotFoundException {
        // given
        PlayerScore ps = new PlayerScore("615e009a1e947e29fc97038a", "Coyote12", 13);
        // then
        assertThrows(UsernameNotFoundException.class, () -> {
            service.delete("615e009a1e947e29fc970111");
        });
    }
}