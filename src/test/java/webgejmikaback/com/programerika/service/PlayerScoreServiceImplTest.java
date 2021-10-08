package webgejmikaback.com.programerika.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webgejmikaback.com.programerika.converter.PlayerScoresConverter;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlayerScoreServiceImplTest {

    @MockBean
    private PlayerScoresRepository repository;

    @Autowired
	private PlayerScoreServiceImpl service;

    @Test
    @DisplayName("Test getByUsername Success")
    public void testGetByUsername() throws UsernameNotFoundException {
        PlayerScore ps = new PlayerScore("","bole55",13);
        when(repository.findByUsername("bole55")).thenReturn(java.util.Optional.of(ps));
        PlayerScore returnedPS = service.getByUsername("bole55");
        verify(repository).findByUsername("bole55");
        assertNotNull(returnedPS);
    }
}