package webgejmikaback.com.programerika.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.service.PlayerScoreServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(PlayerScoresController.class)
//@SpringBootTest
class PlayerScoresControllerTest {

    @MockBean
    private PlayerScoreServiceImpl playerScoreService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void savePlayerScore() {
        PlayerScore ps = new PlayerScore("UID123", "bole55", 13);
        Mockito.when(playerScoreService.savePlayerScore(Mockito.any()))
                .thenReturn(ps);
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/player-scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ps))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", Matchers.is("UID123")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArgumentCaptor<PlayerScore> argument = ArgumentCaptor.forClass(PlayerScore.class);
        Mockito.verify(playerScoreService).savePlayerScore(argument.capture());
        assertEquals(argument.getValue().getUsername(),ps.getUsername());
    }
}