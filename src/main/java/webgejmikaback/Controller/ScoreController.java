package webgejmikaback.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import webgejmikaback.Model.PlayerScore;
import webgejmikaback.Repository.PlayerRepository;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ScoreController {

    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping(value = "/savePlayer")
    public String saveScore(){
        playerRepository.saveAll(Arrays.asList(new PlayerScore("Bane",21),new PlayerScore("Nata",22)));
        return "Player have been successfully saved";
    }

    @RequestMapping(value = "/deletescores")
    public String emptyDB(){
        playerRepository.deleteAll();
        return "All scores have been deleted";
    }


    @RequestMapping(value = "/getallscores",method = RequestMethod.GET)
    public List<PlayerScore> getAll(){
        return playerRepository.findAll();
    }
}
