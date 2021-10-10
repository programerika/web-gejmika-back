package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    public void testGetByUsernameSuccess() {
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
    
}