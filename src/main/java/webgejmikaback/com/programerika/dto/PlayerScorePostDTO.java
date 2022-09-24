package webgejmikaback.com.programerika.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerScorePostDTO {
    @Pattern(regexp = "(?i)[a-z0-9]{4,6}[0-9]{2}", message = "Username must start with min 4 and max 6 letters or digits, followed by 2 digits")
    @NotNull
    private String username;
    @NotNull
    private Integer score;
}
