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
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.service.PlayerScoreService;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    @DisplayName("Test savePlayerScore throws an exception for field bad validation")
    void testSavePlayerScoreThrowsAnException_For_FieldBadValidation() throws Exception {
        PlayerScore ps = new PlayerScore("testUID", "bole555a", 13);
        Mockito.when(playerScoreService.savePlayerScore(Mockito.any()))
                .thenReturn(ps);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/player-scores")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ps))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type", Matchers.is("UsernameBadValidationException")));
    }
}