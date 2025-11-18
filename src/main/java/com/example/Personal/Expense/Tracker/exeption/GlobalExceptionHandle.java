package com.example.Personal.Expense.Tracker.exeption;

import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {

//    @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<String> handlingRuntimeException(RuntimeException e){
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse> handlingAppException(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        APIResponse apiResponse = new APIResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
     @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse> handingValidation(MethodArgumentNotValidException e){
        String enumKey =e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
     }
}
