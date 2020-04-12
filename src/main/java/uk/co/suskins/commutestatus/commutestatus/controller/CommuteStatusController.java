package uk.co.suskins.commutestatus.commutestatus.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.suskins.commutestatus.common.controller.BaseController;
import uk.co.suskins.commutestatus.commutestatus.models.api.CommuteStatusResponse;
import uk.co.suskins.commutestatus.commutestatus.service.CommuteStatusService;

import java.security.Principal;

@RestController
public class CommuteStatusController extends BaseController {
    private final CommuteStatusService commuteStatusService;

    @Autowired
    public CommuteStatusController(CommuteStatusService commuteStatusService) {
        this.commuteStatusService = commuteStatusService;
    }

    @GetMapping("secure/commutestatus")
    @ApiOperation(value = "Returns the calling users commute status.",
            authorizations = {@Authorization(value = "auth0")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Commute status successfully returned."),
            @ApiResponse(code = 401, message = "Unauthorised."),
            @ApiResponse(code = 404, message = "User could not be found."),
            @ApiResponse(code = 500, message = "Internal error."),
    })
    public CommuteStatusResponse getCommuteStatus(@ApiParam(hidden = true)
                                                          Principal principal) {
        return commuteStatusService.getCommuteStatus(principal);
    }
}

