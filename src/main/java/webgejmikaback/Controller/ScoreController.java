package webgejmikaback.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webgejmikaback.Model.PlayerScore;
import webgejmikaback.Repository.PlayerRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ScoreController {

    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping(value = "/savePlayer",method = RequestMethod.GET)
    public String saveScore() {
        // 21 21 21 13 8
        PlayerScore ps = new PlayerScore("Nata", Arrays.asList(13,8,13,13,21,13,21));
        PlayerScore ps1 = new PlayerScore("Pera", Arrays.asList(13,8,8,13,0,21));
        PlayerScore ps2 = new PlayerScore("Bane", Arrays.asList(13,8,13,13,21,8,21,21));
        playerRepository.saveAll(Arrays.asList(ps,ps1,ps2));
        return "Players have been successfully saved";
    }

    @RequestMapping(value = "/deletescores", method = RequestMethod.DELETE)
    public String deleteAllScores() {
        playerRepository.deleteAll();
        return "All scores have been deleted";
    }

    @RequestMapping(value = "/getallscores", method = RequestMethod.GET)
    public List<PlayerScore> getAllScores() {
        return playerRepository.findAll();
    }

    @RequestMapping(value = "/getplayerbyusername", method = RequestMethod.GET)
    public Object getPlayerByUserName(@RequestHeader(name = "username") String username) {
        try{
            if(playerRepository.findById(username).isPresent()){
                return playerRepository.findById(username);
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            return "Player with name " + username + " not found";
        }
    }

    @RequestMapping(value = "/uploadscore", method = RequestMethod.POST)
    public String createScore(@RequestBody PlayerScore playerScore) {
        playerRepository.save(playerScore);
        return "Player score has been added.";
    }

    @RequestMapping(value = "/gettopten",method = RequestMethod.GET)
    public Object top10PlaysPerPlayer(@RequestHeader(name = "username") String username) {
        try {
            return playerRepository.findById(username).get().getScore().stream().sorted(Comparator.reverseOrder()).limit(10).collect(Collectors.toList());
        } catch (Exception e) {
            return "Player with name " + username + " not found";
        }
    }

}
