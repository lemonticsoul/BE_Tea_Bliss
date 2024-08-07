package store.teabliss.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import store.teabliss.member.controller.MemberController;
import store.teabliss.member.dto.MemberResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {

    @ExceptionHandler(DuplicationMemberEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<MemberResponse> handler(DuplicationMemberEmailException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .data(e.getEmail())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundMemberByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<MemberResponse> handler(NotFoundMemberByIdException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .data(e.getMemberId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DuplicationNicknameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<MemberResponse> handler(DuplicationNicknameException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .data(e.getNickname())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotEqualPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<MemberResponse> handler(NotEqualPasswordException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<MemberResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : e.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatusCode().value())
                .message("fail")
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
