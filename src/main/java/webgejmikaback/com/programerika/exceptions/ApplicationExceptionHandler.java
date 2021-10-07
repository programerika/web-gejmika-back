package webgejmikaback.com.programerika.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.common.MediaTypes;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.net.URI;
import java.util.Optional;

@ControllerAdvice
public class ApplicationExceptionHandler implements ProblemHandling {

    @Override
    public boolean isCausalChainsEnabled() {
        return false;
    }



    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Problem> handlePlayerNotFoundException(String message) {
        return ResponseEntity.of(
            Optional.of(
                Problem.builder()
                    .withType(URI.create("https://webgejmikaback.com/player-score/username-not-found"))
                    .withTitle("Username not found")
                    .withDetail("There is no username in the repository")
                    .withStatus(Status.NOT_FOUND)
                    .build()
            ));
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<Problem> handlePlayerAlreadyExistsException(PlayerAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem.builder()
                        .withTitle("Username already exists")
                        .withDetail(e.getMessage())
                        .withStatus(Status.CONFLICT)
                        .build());
//        return ResponseEntity.of(
//            Optional.of(
//                Problem.builder()
//                    .withType(URI.create("https://webgejmikaback.com/player-score/username-already-exists"))
//                    .withTitle("Username already exists")
//                    .withDetail("The username is already in the repository")
//                    .withStatus(Status.CONFLICT)
//                    .build()
//            ));
    }

}