package webgejmikaback.com.programerika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "scoresTestWithMap")
public class PlayerScore {
    @Id
    @JsonIgnore
    private String uid;
    @Pattern(regexp = "(?i)[a-z0-9]{4,6}[0-9]{2}", message = "Username must start with min 4 and max 6 letters or digits, followed by 2 digits")
    @NotNull
    private String username;
    @NotNull
    private Integer score;
    @NotNull
    private Map<String,Integer> scores;
}
