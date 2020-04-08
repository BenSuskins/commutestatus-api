package co.uk.suskins.commutestatus.common.models.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCodes {
    UNKNOWN_ERROR(0, "UNKNOWN_ERROR", "An unknown error has occurred, please contact support", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR(1, "DATABASE_ERROR", "A database error occurred, please contact support", HttpStatus.INTERNAL_SERVER_ERROR);

    private Integer errorCode;
    private String errorTitle;
    private String errorMessage;
    private HttpStatus httpStatus;

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
