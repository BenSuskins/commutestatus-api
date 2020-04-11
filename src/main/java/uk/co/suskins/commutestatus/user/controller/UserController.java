package uk.co.suskins.commutestatus.user.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.suskins.commutestatus.common.controller.BaseController;
import uk.co.suskins.commutestatus.user.models.api.UserRequest;
import uk.co.suskins.commutestatus.user.service.UserService;

@RestController
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create a new user entity and user preferences")
    public void postUser(UserRequest userRequest) {
        userService.postUser(userRequest);
    }

    @PutMapping("/secure/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Create a new user entity and user preferences")
    public void putUser(@PathVariable("userId") Long userId, UserRequest userRequest) {
        userService.putUser(userId, userRequest);
    }
}
