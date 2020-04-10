package uk.co.suskins.commutestatus.common.models.exceptions;

public class CommuteStatusServiceException extends RuntimeException {
    public CommuteStatusServiceException(ErrorCodes errorCodes) {
        super(errorCodes.getErrorMessage());
    }
}
