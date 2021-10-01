package webgejmikaback.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import webgejmikaback.Model.Player;
import webgejmikaback.Model.PlayerDTO;
import webgejmikaback.Service.PlayerService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/")
public class PlayerScoresController {

    private final PlayerService playerService;

    public PlayerScoresController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "Delete score by uid", description = "It deletes player's score from all-scores collection.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No content",
                    content = { @Content(schema = @Schema(implementation = Void.class))}),
//                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    // path variabla i response 204
    @RequestMapping(value = "player-scores/{uid}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(name = "username") String username) {
        playerService.delete(username);
        return "Score is deleted";
    }

    @Operation(summary = "Get player by username", description = "Provide username to get player with score from all-scores collection.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "player-scores/{username}", method = RequestMethod.GET)
    public Optional<Player> getPlayerByUserName(@PathVariable(name = "username") String username) {
        return playerService.getPlayerByUsername(username);
    }

    @Operation(summary = "Save player score", description = "Provide username and score to save the new player's score")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))},
                    headers = {@Header(name = "Location")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "player-scores", method = RequestMethod.POST)
    public void playerScore(@Valid @RequestBody Player player) {
        playerService.saveScore(player);
    }

    @Operation(summary = "Add player score", description = "Provide username and new score to add score to existing one")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No content",
                    content = { @Content(schema = @Schema(implementation = Void.class))}),
//                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    // ubaciti path variablu za username
    @RequestMapping(value = "player-scores/{username}/add-score", method = RequestMethod.POST)
    public void addScore(@RequestBody Player player) {

    }

    @Operation(summary = "Top score players", description = "Method gets 10 or more top score players")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "top-score", method = RequestMethod.GET)
    public List<PlayerDTO> getTopScore() {
        return playerService.getTopTenRanked();
    }

}