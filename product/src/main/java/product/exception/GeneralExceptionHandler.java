package product.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handle(ProductNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handle(FeignException exception) {

        int status = exception.status();
        if (status <= 0) {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        String errorMessage = exception.contentUTF8();


        if (errorMessage == null || errorMessage.isBlank()) {
            errorMessage = exception.getMessage();
        }

        return ResponseEntity.status(status).body(errorMessage);
    }
}
