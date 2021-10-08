package webgejmikaback;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import webgejmikaback.com.programerika.converter.PlayerScoresConverter;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.repository.PlayerScoresRepository;
import webgejmikaback.com.programerika.service.PlayerScoreServiceImpl;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
//@ContextConfiguration(classes = {PlayerScoreServiceImpl.class, PlayerScoresRepository.class})
class WebGejmikaBackApplicationTests {

//    @Mock
//    private PlayerScoresRepository repository;
//
//    @Mock
//    private PlayerScoresConverter converter;
//
//    @InjectMocks
//	private PlayerScoreServiceImpl service;
//
//    @Test
//    public void testGetByUsername() throws UsernameAlreadyExistsException, UsernameNotFoundException {
//        repository = (PlayerScoresRepository) Arrays.asList(
//                new PlayerScore("","bole55",13),
//                new PlayerScore("","Milan88",21),
//                new PlayerScore("","Naca97",42),
//                new PlayerScore("","baki78",8)
//        );
//        service = new PlayerScoreServiceImpl(repository,converter);
//        PlayerScore ps = new PlayerScore();
//        ps.setUsername("bole55");
//        ps.setScore(13);
//        when(repository.findByUsername("bole55")).thenReturn(java.util.Optional.of(ps));
//        PlayerScore response = service.getByUsername("bole55");
//        verify(repository).findByUsername("bole55");
//        assertNotNull(response);
//    }

//	@Test
//	public void testSavePlayerScore() throws UsernameAlreadyExistsException {
//	    PlayerScore ps = new PlayerScore();
//	    when(repository.findByUsername(ps.getUsername())).
//        PlayerScoreDTO dto = service.savePlayerScore(ps);
//    }

}
