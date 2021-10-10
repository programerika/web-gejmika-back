package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlayerScoreServiceImplTest {

    @MockBean
    private PlayerScoresRepository repository;

    @Autowired
	private PlayerScoreServiceImpl service;

    @Test
    @DisplayName("Test getByUsername Success")
    public void testGetByUsernameSuccess() {

        PlayerScore ps = new PlayerScore("","bole55",13);
        when(repository.findByUsername("bole55"))
            .thenReturn(Optional.of(ps));

        PlayerScore expected = null;
        try {
            expected = service.getByUsername("bole55");
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        verify(repository).findByUsername("bole55");

        assertNotNull(expected);
    }
}