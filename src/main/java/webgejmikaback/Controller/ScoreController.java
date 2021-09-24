package webgejmikaback.Controller;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/v1/player")
public class ScoreController {

    private final PlayerService playerService;

    public ScoreController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "Saves multiple scores", description = "Provide list of players")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Scores are saved",
                     content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
        @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/save-all-scores",method = RequestMethod.POST)
    public String saveAll() {
        playerService.saveAll();
        return "All Scores have been successfully saved";
    }

    @Operation(summary = "Delete score by username", description = "It deletes player's score from all-scores collection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Scores are deleted",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public String delete(@RequestParam(name = "username") String username) {
        playerService.delete(username);
        return "Score is deleted";
    }

    @Operation(summary = "Delete all scores", description = "It deletes all scores from all-scores collection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Scores are deleted",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/delete-all-scores", method = RequestMethod.DELETE)
    public String deleteAll() {
        playerService.deleteAll();
        return "All scores deleted";
    }

    @Operation(summary = "Get all scores", description = "It gets all scores that are saved in all-scores collection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All scores",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/all-scores", method = RequestMethod.GET)
    public List<Player> getAll() {
        return playerService.getAll();
    }

    @Operation(summary = "Get all ranked scores", description = "It gets all scores that are saved in all-scores collection with rank.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All scores",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/all-ranked-scores", method = RequestMethod.GET)
    public List<PlayerDTO> getAllRanked() {
        return playerService.getAllRanked();
    }

    @Operation(summary = "Get player by username", description = "Provide username to get player with score from all-scores collection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Player with score",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Optional<Player> getPlayerByUserName(@RequestParam(name = "username") String username) {
        return playerService.getPlayerByUsername(username);
    }

    @Operation(summary = "Save or update player score", description = "Provide username and score to save the new player or update the existing one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "New score is saved",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveScore(@Valid @RequestBody Player player) {
        playerService.saveScore(player);
    }

    @Operation(summary = "Top ten ranked players with username position", description = "Method gets top ten ranked players, including username's position, even if it's not in top ten positions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Top ten players",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/top-score/{username}", method = RequestMethod.GET)
    public List<PlayerDTO> getTopTen(@RequestParam(name = "username") String username) {
        return playerService.getTopTen(username);
    }

    @Operation(summary = "Get ranked player by username", description = "Provide username to get player's rank, username and score from all-scores collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ranked player",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/ranked/{username}", method = RequestMethod.GET)
    public PlayerDTO getRanked(@RequestParam(name = "username") String username) {
        return playerService.getRankedScore(username);
    }

    @Operation(summary = "Top ten ranked players", description = "Method gets top ten ranked players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Top ten ranked players",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/top-score-ranked", method = RequestMethod.GET)
    public List<PlayerDTO> getTopTenRanked() {
        return playerService.getTopTenRanked();
    }

}