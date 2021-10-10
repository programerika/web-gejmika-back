package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
class PlayerScoreServiceImplTest {

    @Mock
    private PlayerScoresRepository repository;
    private PlayerScoreServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new PlayerScoreServiceImpl(repository);
    }

    @Test
    @DisplayName("Test getByUsername Success")
    public void canGetByUsernameSuccess() {
        // given
        PlayerScore ps = new PlayerScore("","bole55",13);
        PlayerScore expected = null;
        // when
        when(repository.findByUsername("bole55"))
                .thenReturn(Optional.of(ps));
        try {
            expected = underTest.getByUsername("bole55");
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
//    @Disabled
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
    @Disabled
    void canAddScore() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("Test delete Success")
    @Disabled
    void canDeletePlayerScoreByUID() {
        // given
        // when
        // then
    }
}