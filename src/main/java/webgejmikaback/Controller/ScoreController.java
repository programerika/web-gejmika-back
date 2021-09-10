package webgejmikaback.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import webgejmikaback.Model.Player;
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

    @Operation(summary = "Delete all scores", description = "It deletes all scores from all-scores collection")
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
        return "All scores have been deleted";
    }

    @Operation(summary = "Get all scores", description = "It gets all scores that are saved in all-scores collection")
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

    @Operation(summary = "Get player by user name", description = "Provide username to get player with score from all-scores collection")
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

//    @Operation(summary = "Save score", description = "Provide new player")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Score is saved",
//                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
//    })
//    @RequestMapping(value = "/saveScore", method = RequestMethod.POST)
//    public Player saveScore(@RequestBody Player player) {
//        return playerService.saveScore(player);
//    }

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

//    @Operation(summary = "Update score", description = "Provide a new score to update the old one")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Score is updated",
//                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
//    })
//    @RequestMapping(value = "/updateScore", method = RequestMethod.PUT)
//    public Optional<Player> updateScore(@RequestBody Player player) {
//        return playerService.updateScore(player);
//    }

    @Operation(summary = "Top ten players", description = "Method gets top ten players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Top ten players",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))})
    })
    @RequestMapping(value = "/top-score", method = RequestMethod.GET)
    public List<Player> getTopTen() {
        return playerService.getTopTen();
    }

}