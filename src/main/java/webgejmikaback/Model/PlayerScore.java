package webgejmikaback.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "all-scores")
public class PlayerScore {
    @Id
    @Pattern(regexp = "[a-zA-Z]{2}\\d{6}", message = "Username must start with 2 letters followed by 6 digits")
    private String username;
    private Integer score;
}
