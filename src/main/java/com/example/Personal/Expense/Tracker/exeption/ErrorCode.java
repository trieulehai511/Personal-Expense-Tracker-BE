package com.example.Personal.Expense.Tracker.exeption;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_CORRECT(1009, "Old password is not correct", HttpStatus.BAD_REQUEST),
    //Permissions
    PERMISSION_NOT_EXISTED(2001, "Permission is not existed", HttpStatus.NOT_FOUND),

    //Category
    CATEGORY_NOT_EXISTED(2002, "Category is not existed", HttpStatus.NOT_FOUND),
    CATEGORY_CANNOT_DELETE(2003, "Category cannot be deleted", HttpStatus.BAD_REQUEST),

    //Expense
    EXPENSE_NOT_EXISTED(3003, "Expense is not existed", HttpStatus.NOT_FOUND),

    DATA_API_ERROR(36, "Internal data access usage error", HttpStatus.BAD_REQUEST)
    ;


    private int code;
    private String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message,HttpStatusCode statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
