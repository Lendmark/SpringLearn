package pl.lendemark.bookaro;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

class CustomGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Optional> handleException(MethodArgumentNotValidException ex){
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + " - " + x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, status);

    }
}
