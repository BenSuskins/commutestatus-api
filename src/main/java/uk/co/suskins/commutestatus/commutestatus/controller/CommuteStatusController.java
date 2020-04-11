package uk.co.suskins.commutestatus.commutestatus.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.suskins.commutestatus.common.controller.BaseController;
import uk.co.suskins.commutestatus.commutestatus.models.api.CommuteStatusResponse;
import uk.co.suskins.commutestatus.commutestatus.service.CommuteStatusService;

@RestController
public class CommuteStatusController extends BaseController {
    private final CommuteStatusService commuteStatusService;

    @Autowired
    public CommuteStatusController(CommuteStatusService commuteStatusService) {
        this.commuteStatusService = commuteStatusService;
    }

    @GetMapping("/secure/commutestatus")
    @ApiOperation("Returns the calling users commute status")
    public CommuteStatusResponse postUser() {
        return null;
    }
}

