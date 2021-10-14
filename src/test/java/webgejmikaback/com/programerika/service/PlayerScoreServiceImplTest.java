package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerScoreServiceImplTest {

    @Mock
    private PlayerScoresRepository repository;

    @InjectMocks
    private PlayerScoreServiceImpl underTest;

//    @BeforeEach
//    void setUp() {
//        underTest = new PlayerScoreServiceImpl(repository);
//    }

    @Test
    @DisplayName("Test getByUsername Success")
    public void canGetPlayerScoreByUsernameSuccess() {
        // given
        PlayerScore ps = new PlayerScore("","bole55",13);
        PlayerScore expected = null;
        // when
        when(repository.findByUsername(anyString()))
                .thenReturn(Optional.of(ps));
        try {
            expected = underTest.getByUsername(ps.getUsername());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        // then
        assertNotNull(expected);
        assertEquals("bole55",expected.getUsername());
        verify(repository).findByUsername("bole55");
    }

    @Test
    @DisplayName("Test savePlayerScore Success")
    void canSavePlayerScore() {
        // given
        PlayerScore ps = new PlayerScore("", "bole55", 13);
        PlayerScore saved = null;
        // when
        when(repository.save(any(PlayerScore.class)))
                .thenReturn(ps);
        try {
            saved = underTest.savePlayerScore(ps);
        } catch (UsernameAlreadyExistsException e) {
            e.printStackTrace();
        }
        // then
        assertNotNull(saved);
        assertEquals("bole55",saved.getUsername());
        verify(repository).save(saved);
    }

    @Test
    @DisplayName("Test getTopScore Success")
    void canGetTopScore() {
        // given
        PlayerScore ps1 = new PlayerScore("", "bole55", 13);
        PlayerScore ps2 = new PlayerScore("", "udzej11", 8);
        PlayerScore ps3 = new PlayerScore("", "naki12", 21);
        List<PlayerScore> list = Arrays.asList(ps1,ps2,ps3);
        repository.saveAll(list);
        // when
        when(repository.getTopScore()).thenReturn(list);
        List<PlayerScore> expected = underTest.getTopScore();
        // then
        assertNotNull(list);
        assertEquals(list,expected);
        verify(repository).getTopScore();
    }

    @Test
    @DisplayName("Test addPlayerScore Success")
    void canAddScore() {
        // given
        PlayerScore ps = new PlayerScore("", "Nadja12", 13);
        Integer initScore = ps.getScore();
        Integer addedScore = 0;
        Integer score = 21;
        // when
        when(repository.findByUsername(anyString()))
                .thenReturn(Optional.of(ps));
        try {
            underTest.addPlayerScore(ps.getUsername(),score);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        // then
        addedScore = initScore + score;
        assertNotNull(ps.getUsername());
        assertNotNull(score);
        assertEquals("Nadja12",ps.getUsername());
        assertEquals(addedScore,initScore+score);
        verify(repository).save(ps);
    }

    @Test
    @DisplayName("Test delete Success")
    void canDeletePlayerScoreByUID() throws UsernameNotFoundException {
        // given
        PlayerScore ps = new PlayerScore("615e009a1e947e29fc97038a", "Coyote12", 13);
        // when
        when(repository.existsById(anyString())).thenReturn(true);
        underTest.delete(ps.getUid());
        // then
        assertNotNull(ps.getUid());
        assertEquals("615e009a1e947e29fc97038a",ps.getUid());
        verify(repository).deleteById(ps.getUid());
    }
}