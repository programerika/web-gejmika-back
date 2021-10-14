package webgejmikaback.com.programerika.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.UsernameAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.UsernameBadValidationException;
import webgejmikaback.com.programerika.exceptions.UsernameNotFoundException;
import webgejmikaback.com.programerika.exceptions.ScoreOutOfRangeException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.service.PlayerScoreServiceImpl;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/v1/")
public class PlayerScoresController {

    private final PlayerScoreServiceImpl playerScoreService;

    public PlayerScoresController(PlayerScoreServiceImpl playerScoreService) {
        this.playerScoreService = playerScoreService;
    }

    @Operation(summary = "Delete score by uid", description = "It deletes player's score from all-scores collection.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No content",
                    content = { @Content(schema = @Schema(implementation = Void.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "player-scores/{uid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(name = "uid") String uid) throws UsernameNotFoundException {
            playerScoreService.delete(uid);
            return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get player by username", description = "Provide username to get player with score from all-scores collection.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "player-scores/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerByUserName(@PathVariable(name = "username") String username) throws UsernameNotFoundException {
            Optional<PlayerScore> optional = Optional.ofNullable(playerScoreService.getByUsername(username));
            return ResponseEntity.of(optional);
    }

    @Operation(summary = "Save player score", description = "Provide username and score to save the new player's score")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScoreDTO.class))},
                    headers = {@Header(name = "Location")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScoreDTO.class))}),
            @ApiResponse(
                    responseCode = "406",
                    description = "Not Acceptable",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))}),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScoreDTO.class))})
    })
    @RequestMapping(value = "player-scores", method = RequestMethod.POST)
    public ResponseEntity<?> savePlayerScore(@Valid @RequestBody PlayerScore playerScore,BindingResult result) throws UsernameAlreadyExistsException, UsernameBadValidationException, ScoreOutOfRangeException {
        if (result.hasErrors()){
            throw new UsernameBadValidationException("Username bad validation. Username must start with min 4 and max 6 letters, followed by 2 digits.");
        }
        PlayerScoreDTO dto = new PlayerScoreDTO();
            PlayerScore ps = playerScoreService.savePlayerScore(playerScore);
        dto.setId(ps.getUid());
        URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(dto.getId())
                    .toUri();
            return ResponseEntity.created(location).body(dto);
    }

    @Operation(summary = "Add player score", description = "Provide username and new score to add score to existing one")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content",
                    content = { @Content(schema = @Schema(implementation = Void.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))}),
            @ApiResponse(
                    responseCode = "406",
                    description = "Not Acceptable",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "player-scores/{username}/add-score", method = RequestMethod.POST)
    public ResponseEntity<?> addScore(@PathVariable(name = "username") String username, @RequestBody Integer score) throws UsernameNotFoundException, ScoreOutOfRangeException {
            playerScoreService.addPlayerScore(username, score);
            return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Top score players", description = "Method gets 10 or more top score players")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "top-score", method = RequestMethod.GET)
    public ResponseEntity<List<PlayerScore>>  getTopScore() {
        return ResponseEntity.ok(playerScoreService.getTopScore());
    }

}