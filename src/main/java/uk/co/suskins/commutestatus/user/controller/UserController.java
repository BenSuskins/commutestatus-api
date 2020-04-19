package uk.co.suskins.commutestatus.user.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.suskins.commutestatus.common.controller.BaseController;
import uk.co.suskins.commutestatus.user.models.api.UserReponse;
import uk.co.suskins.commutestatus.user.models.api.UserRequest;
import uk.co.suskins.commutestatus.user.service.UserService;

import java.security.Principal;

@RestController
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("public/user")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new user entity and user preferences.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully created."),
            @ApiResponse(code = 500, message = "Internal error."),
    })
    public void postUser(@RequestBody UserRequest userRequest) {
        userService.postUser(userRequest);
    }

    @PutMapping("secure/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Updates a user / user preference.",
            authorizations = {@Authorization(value = "auth0")})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User successfully updated."),
            @ApiResponse(code = 404, message = "User could not be found."),
            @ApiResponse(code = 500, message = "Internal error."),
    })
    public void putUser(@ApiParam(hidden = true)
                                Principal principal, @RequestBody UserRequest userRequest) {
        userService.putUser(principal, userRequest);
    }

    @GetMapping("secure/user")
    @ApiOperation(value = "Returns the user.",
            authorizations = {@Authorization(value = "auth0")})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User successfully found."),
            @ApiResponse(code = 404, message = "User could not be found."),
            @ApiResponse(code = 500, message = "Internal error."),
    })
    public UserReponse getUser(@ApiParam(hidden = true)
                                       Principal principal) {
        return userService.getUser(principal);
    }
}
