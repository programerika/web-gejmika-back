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
    @Pattern(regexp = "[a-zA-Z]{4}\\d{2}", message = "Username must start with 4 letters followed by 2 digits")
//    @Pattern(regexp = "[a-zA-Z0-9]*", message = "Username must consist of 4 letters and 4 digits and no special characters")
    private String username;
    private Integer score;
}
