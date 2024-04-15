package dev.ebrydeu.spring_boot_library.exception;

import dev.ebrydeu.spring_boot_library.exception.Exceptions.EmailAlreadyExistsException;
import dev.ebrydeu.spring_boot_library.responses.JSendResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static dev.ebrydeu.spring_boot_library.exception.Exceptions.InternalServerErrorException;
import static dev.ebrydeu.spring_boot_library.exception.Exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<JSendResponse> notFoundException(NotFoundException ex) {

        var response = JSendResponse.error(ex.getMessage(), 404, null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<JSendResponse> emailAlreadyExistsException(EmailAlreadyExistsException ex) {
        var response = JSendResponse.error(ex.getMessage(), 409, null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<JSendResponse> internalServerErrorException(InternalServerErrorException ex) {
        var response = JSendResponse.error(ex.getMessage(), 500, null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


