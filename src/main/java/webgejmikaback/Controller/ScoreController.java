package webgejmikaback.Controller;

import org.springframework.web.bind.annotation.*;
import webgejmikaback.Model.PlayerScore;
import webgejmikaback.service.PlayerService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ScoreController {

    private final PlayerService playerService;

    public ScoreController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(value = "/saveAllScores",method = RequestMethod.GET)
    public String saveAllScores() {
        playerService.saveAllScores();
        return "All Scores have been successfully saved";
    }

    @RequestMapping(value = "/deleteAllScores", method = RequestMethod.DELETE)
    public String deleteAllScores() {
        playerService.deleteAllScores();
        return "All scores have been deleted";
    }

    @RequestMapping(value = "/getAllScores", method = RequestMethod.GET)
    public List<PlayerScore> getAllScores() {
        return playerService.getAllScores();
    }

    @RequestMapping(value = "/getPlayerByUsername", method = RequestMethod.GET)
    public Optional<PlayerScore> getPlayerByUserName(@RequestHeader(name = "username") String username) {
        return playerService.getPlayerByUserName(username);
    }

    @RequestMapping(value = "/saveScore", method = RequestMethod.POST)
    public PlayerScore saveScore(@RequestBody PlayerScore playerScore) {
        return playerService.saveScore(playerScore);
    }

    @RequestMapping(value = "/topTenById",method = RequestMethod.GET)
    public Optional<PlayerScore> getTopTenById(@RequestHeader(name = "username") String username) {
        return playerService.getTopTenById(username);
    }

}
