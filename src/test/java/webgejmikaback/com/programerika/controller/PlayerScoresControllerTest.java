package webgejmikaback.com.programerika.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.service.PlayerScoreService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerScoresController.class)
class PlayerScoresControllerTest {

    @MockBean
    private PlayerScoreService playerScoreService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test savePlayerScore Success")
    void testSavePlayerScoreSuccess() {
        PlayerScore ps = createPlayerScore();
        Mockito.when(playerScoreService.savePlayerScore(Mockito.any(),anyString()))
                .thenReturn(ps);
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/player-scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ps))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", Matchers.is("1234")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArgumentCaptor<PlayerScoreDTO> argument = ArgumentCaptor.forClass(PlayerScoreDTO.class);
        Mockito.verify(playerScoreService).savePlayerScore(argument.capture(),anyString());
        assertEquals(argument.getValue().getUsername(),ps.getUsername());
    }

    @Test
    @DisplayName("Test savePlayerScore throws an exception for field bad validation")
    void testSavePlayerScoreThrowsAnException_For_FieldBadValidation() throws Exception {
        PlayerScore ps = new PlayerScore("testUID", "bole555a", 13,new HashMap<>());
        Mockito.when(playerScoreService.savePlayerScore(Mockito.any(),anyString()))
                .thenReturn(ps);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/player-scores")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ps))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type", Matchers.is("UsernameBadValidationException")));
    }

    private PlayerScore createPlayerScore() {

        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(21);
        playerScore.setScores(new HashMap<>());
        playerScore.setUid("1234");
        playerScore.setUsername("Jovan55");

        Map<String, Integer> map = new HashMap<>();
        map.put("gejmika", 21);
        playerScore.setScores(map);

        return playerScore;
    }
}