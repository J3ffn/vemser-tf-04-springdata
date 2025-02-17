package br.com.dbc.wbhealth.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    private <T extends Exception> Map<String, Object> createBody(T exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", exception.getMessage());
        return body;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception,
                                                  HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createBody(exception));
    }

    @ExceptionHandler(BancoDeDadosException.class)
    public ResponseEntity<Object> handleException(BancoDeDadosException exception,
                                                  HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createBody(exception));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleException(NullPointerException exception,
                                                  HttpServletRequest request) {

        Map<String, Object> body = createBody(exception);

        body.replace("message", "Id inválido!");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleException(NegocioException exception) {

        Map<String, Object> body = createBody(exception);
        body.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<Object> handleException(IndexOutOfBoundsException exception,
                                                  HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createBody(exception));
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<Object> handleException(EntityNotFound exception,
                                                  HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createBody(exception));
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<Object> hancleException(DataInvalidaException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createBody(exception));
    }
}
