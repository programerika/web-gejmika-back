package webgejmikaback.com.programerika.exceptions;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.util.Optional;

@ControllerAdvice
class ApplicationExceptionHandler implements ProblemHandling {

//    @ExceptionHandler(PlayerNotFoundException.class)
//    public ResponseEntity<Problem> handleDemoNotWorkingException() {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .headers(HttpHeaders.CONTENT_TYPE, Media)
//                .
//
//    }

}