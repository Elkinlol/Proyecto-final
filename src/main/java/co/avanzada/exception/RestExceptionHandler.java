package co.avanzada.exception;


import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.extras.ValidationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseDTO<String>> noResourceFoundExceptionHandler(NoResourceFoundException ex){
        return ResponseEntity.status(404).body( new ResponseDTO<>(true, "El recurso solicitado no debe existir") );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> generalExceptionHandler (Exception e){
        return ResponseEntity.internalServerError().body( new ResponseDTO<>(true, e.getMessage()) );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<List<ValidationDTO>>> validationExceptionHandler (MethodArgumentNotValidException ex ) {
        List<ValidationDTO> errors = new ArrayList<>();
        BindingResult results = ex.getBindingResult();
        for (FieldError e: results.getFieldErrors()) {
            errors.add( new ValidationDTO(e.getField(), e.getDefaultMessage()) );
        }
        return ResponseEntity.badRequest().body( new ResponseDTO<>(true, errors) );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(new ResponseDTO<>(true, ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseDTO<String>> handleConflict(ConflictException ex) {
        return ResponseEntity.status(409).body(new ResponseDTO<>(true, ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO<String>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(new ResponseDTO<>(true, "No tienes permisos para acceder a este recurso"));
    }

    @ExceptionHandler(UnatorizedException.class)
    public ResponseEntity<ResponseDTO<String>> handleUnatorizedException(UnatorizedException ex) {
        return ResponseEntity.status(401).body(new ResponseDTO<>(true, ex.getMessage()));
    }
}
