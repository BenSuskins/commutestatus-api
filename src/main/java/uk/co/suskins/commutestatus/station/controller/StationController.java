package uk.co.suskins.commutestatus.station.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.suskins.commutestatus.common.controller.BaseController;
import uk.co.suskins.commutestatus.station.models.api.StationResponse;
import uk.co.suskins.commutestatus.station.service.StationService;

@RestController
public class StationController extends BaseController {
    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/stations")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Returns all stations and their CRS codes")
    public StationResponse getStations() {
        return stationService.getStations();
    }
}
