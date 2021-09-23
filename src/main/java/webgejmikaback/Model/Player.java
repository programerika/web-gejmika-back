package webgejmikaback.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "scores")
public class Player {
    @Id
    @Pattern(regexp = "(?i)[a-z]{4,6}[0-9]{2}", message = "Username must start with min 4 and max 6 letters, followed by 2 digits")
    private String username;
    private Integer score;
}
