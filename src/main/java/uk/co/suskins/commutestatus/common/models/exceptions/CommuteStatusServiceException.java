package uk.co.suskins.commutestatus.common.models.exceptions;

public class CommuteStatusServiceException extends RuntimeException {
    ErrorCodes errorCodes;

    public CommuteStatusServiceException(ErrorCodes errorCodes) {
        super(errorCodes.getErrorMessage());
        this.errorCodes = errorCodes;
    }
}
