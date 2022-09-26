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
                .body(Problem
                        .builder()
                        .withTitle("Not Found")
                        .withType(URI.create(e.getClass().getSimpleName()))
                        .withDetail(e.getMessage())
                        .withStatus(Status.NOT_FOUND)
                        .build());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Problem> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem
                        .builder()
                        .withTitle("Conflict")
                        .withType(URI.create(e.getClass().getSimpleName()))
                        .withDetail(e.getMessage())
                        .withStatus(Status.CONFLICT)
                        .build());
    }

    @ExceptionHandler(ScoreOutOfRangeException.class)
    public ResponseEntity<Problem> handleScoreOutOfRangeException(ScoreOutOfRangeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem
                        .builder()
                        .withTitle("Not Acceptable")
                        .withType(URI.create(e.getClass().getSimpleName()))
                        .withDetail(e.getMessage())
                        .withStatus(Status.NOT_ACCEPTABLE)
                        .build());
    }

    @ExceptionHandler(UsernameBadValidationException.class)
    public ResponseEntity<Problem> handleUsernameBadValidationException(UsernameBadValidationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem
                        .builder()
                        .withTitle("Bad Request")
                        .withType(URI.create(e.getClass().getSimpleName()))
                        .withDetail(e.getMessage())
                        .withStatus(Status.BAD_REQUEST)
                        .build());
    }

    @ExceptionHandler(UidNotFoundException.class)
    public  ResponseEntity<Problem> handlerUidNotFoundException(UidNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem
                        .builder()
                        .withTitle("Not Found")
                        .withType(URI.create(e.getClass().getSimpleName()))
                        .withDetail(e.getMessage())
                        .withStatus(Status.NOT_FOUND)
                        .build());
    }


    @ExceptionHandler(ProvidedGameNotExistsException.class)
    public ResponseEntity<Problem> NoProvidedGameException(ProvidedGameNotExistsException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem
                        .builder()
                        .withTitle("Not Acceptable")
                        .withType(URI.create(e.getClass().getSimpleName()))
                        .withDetail(e.getMessage())
                        .withStatus(Status.NOT_ACCEPTABLE)
                        .build());
    }

    @ExceptionHandler(UserDoesNotHaveScoreForProvidedGameException.class)
    public ResponseEntity<Problem> UserDoesNotHaveScoreForProvidedGame(UserDoesNotHaveScoreForProvidedGameException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.PROBLEM_VALUE)
                .body(Problem
                        .builder()
                        .withTitle("Not Acceptable")
                        .withType(URI.create(e.getClass().getSimpleName()))
                        .withDetail(e.getMessage())
                        .withStatus(Status.NOT_ACCEPTABLE)
                        .build());
    }
}