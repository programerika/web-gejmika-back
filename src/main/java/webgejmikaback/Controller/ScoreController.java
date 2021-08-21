package webgejmikaback.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import webgejmikaback.Model.PlayerScore;
import webgejmikaback.Service.PlayerService;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/player-score")
public class ScoreController {

    private final PlayerService playerService;

    public ScoreController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "Saves multiple scores", description = "Provide list of players")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Scores are saved",
                     content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "/save-all-scores",method = RequestMethod.POST)
    public String saveAllScores() {
        playerService.saveAllScores();
        return "All Scores have been successfully saved";
    }

    @Operation(summary = "Delete all scores", description = "It deletes all scores from all-scores collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Scores are deleted",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "/delete-all-scores", method = RequestMethod.DELETE)
    public String deleteAllScores() {
        playerService.deleteAllScores();
        return "All scores have been deleted";
    }

    @Operation(summary = "Get all scores", description = "It gets all scores that are saved in all-scores collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All scores",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "/all-scores", method = RequestMethod.GET)
    public List<PlayerScore> getAllScores() {
        return playerService.getAllScores();
    }

    @Operation(summary = "Get player by user name", description = "Provide username to get player with score from all-scores collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Player with score",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Optional<PlayerScore> getPlayerByUserName(@RequestParam(name = "username") String username) {
        return playerService.getPlayerByUserName(username);
    }

//    @Operation(summary = "Save score", description = "Provide new player")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Score is saved",
//                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
//    })
//    @RequestMapping(value = "/saveScore", method = RequestMethod.POST)
//    public PlayerScore saveScore(@RequestBody PlayerScore playerScore) {
//        return playerService.saveScore(playerScore);
//    }

    @Operation(summary = "Save or update player score", description = "Provide username and score to save the new player or update the existing one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "New score is saved",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveScore(@RequestBody PlayerScore playerScore) {
        playerService.saveScore(playerScore);
    }

//    @Operation(summary = "Update score", description = "Provide a new score to update the old one")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Score is updated",
//                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
//    })
//    @RequestMapping(value = "/updateScore", method = RequestMethod.PUT)
//    public Optional<PlayerScore> updateScore(@RequestBody PlayerScore playerScore) {
//        return playerService.updateScore(playerScore);
//    }

    @Operation(summary = "Top ten players", description = "Method gets top ten players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Top ten players",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerScore.class))})
    })
    @RequestMapping(value = "/top-score", method = RequestMethod.GET)
    public List<PlayerScore> getTopTenScores() {
        return playerService.getTopTenScores();
    }

}