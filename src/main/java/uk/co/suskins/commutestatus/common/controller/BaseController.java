package uk.co.suskins.commutestatus.common.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/")
public abstract class BaseController {
    //todo Add generic controller error handling here
}
