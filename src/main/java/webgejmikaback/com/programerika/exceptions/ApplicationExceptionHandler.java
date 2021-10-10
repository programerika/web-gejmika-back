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

@ControllerAdvice
public class ApplicationExceptionHandler implements ProblemHandling {

    @Override
    public boolean isCausalChainsEnabled() {
        return false;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Problem> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem.builder()
                        .withTitle("Not Found")
                        .withDetail(e.getMessage())
                        .withStatus(Status.NOT_FOUND)
                        .build());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Problem> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem.builder()
                        .withTitle("Conflict")
                        .withDetail(e.getMessage())
                        .withStatus(Status.CONFLICT)
                        .build());
    }

    @ExceptionHandler(ScoreOutOfRangeException.class)
    public ResponseEntity<Problem> handleScoreOutOfRangeException(ScoreOutOfRangeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem.builder()
                        .withTitle("Not Acceptable")
                        .withDetail(e.getMessage())
                        .withStatus(Status.NOT_ACCEPTABLE)
                        .build());
    }
}