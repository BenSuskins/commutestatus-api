package uk.co.suskins.commutestatus.common.models.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCodes {
    UNKNOWN_ERROR(100, "UNKNOWN_ERROR",
            "An unknown error has occurred, please contact support.", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTH0_API_ERROR(101, "AUTH0_API_ERROR",
            "An error occured with the Auth0 API.", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTH0_REQUEST_ERROR(102, "AUTH0_REQUEST_ERROR",
            "An error occured with the Auth0 API request.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_STATION_ID(103, "UNKNOWN_STATION_ID",
            "The station ID provided is not recognised.", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR(104, "DATABASE_ERROR",
            "A database error occurred, please contact support.", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_ALREADY_EXISTS(105, "USER_ALREADY_EXISTS",
            "A user already exists for this email address, please try another email address.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(106, "USER_NOT_FOUND",
            "No user was found for the given user ID.", HttpStatus.NOT_FOUND);

    private final Integer errorCode;
    private final String errorTitle;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    ErrorCodes(Integer errorCode, String errorTitle, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
