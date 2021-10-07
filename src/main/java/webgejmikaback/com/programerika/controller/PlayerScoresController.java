package webgejmikaback.com.programerika.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webgejmikaback.com.programerika.dto.PlayerScoreDTO;
import webgejmikaback.com.programerika.exceptions.PlayerAlreadyExistsException;
import webgejmikaback.com.programerika.exceptions.PlayerNotFoundException;
import webgejmikaback.com.programerika.model.PlayerScore;
import webgejmikaback.com.programerika.service.PlayerScoresService;
import webgejmikaback.com.programerika.service.PlayerServiceImpl;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/v1/")
public class PlayerScoresController {

    private final PlayerScoresService playerScoresService;
    private final PlayerServiceImpl playerService;

    public PlayerScoresController(PlayerScoresService playerScoresService, PlayerServiceImpl playerService) {
        this.playerScoresService = playerScoresService;
        this.playerService = playerService;
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
    public ResponseEntity<PlayerScore> delete(@PathVariable(name = "uid") String uid) {
        try {
            playerService.delete(uid);
            return ResponseEntity.noContent().build();
        } catch (PlayerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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
    public ResponseEntity<PlayerScore> getPlayerByUserName(@PathVariable(name = "username") String username) {
        try {
            Optional<PlayerScore> optional = Optional.ofNullable(playerService.getByUsername(username));
            return ResponseEntity.of(optional);
        } catch (PlayerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScoreDTO.class))})
    })
    @RequestMapping(value = "player-scores", method = RequestMethod.POST)
    public ResponseEntity<PlayerScoreDTO> savePlayerScore(@Valid @RequestBody PlayerScore playerScore) {
        PlayerScoreDTO dto = null;
        try {
            dto = playerService.savePlayerScore(playerScore);
        } catch (PlayerAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
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
                    description = "No content",
                    content = { @Content(schema = @Schema(implementation = Void.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "player-scores/{username}/add-score", method = RequestMethod.POST)
    public ResponseEntity<PlayerScore> addScore(@PathVariable(name = "username") String username, @RequestBody Integer score) {
            try {
                playerService.addPlayerScore(username, score);
                return ResponseEntity.noContent().build();
            }catch (PlayerNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
            }catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,ex.getMessage());
            }
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
        return ResponseEntity.ok(playerService.getTopScore());
    }

}