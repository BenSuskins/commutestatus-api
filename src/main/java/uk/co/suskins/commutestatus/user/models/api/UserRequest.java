package uk.co.suskins.commutestatus.user.models.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("User creation request model")
public class UserRequest {
    @ApiModelProperty("Users email address")
    private String email;
    @ApiModelProperty("Users password")
    private String password;
    @ApiModelProperty("Users home railway station ID")
    private Long homeStation;
    @ApiModelProperty("Users work railway station ID")
    private Long workStation;
}
