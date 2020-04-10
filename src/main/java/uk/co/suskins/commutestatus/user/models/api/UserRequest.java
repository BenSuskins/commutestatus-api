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
    @ApiModelProperty("Users first name")
    private String firstName;
    @ApiModelProperty("Users last name")
    private String lastName;
    @ApiModelProperty("Users password")
    private String password;
    @ApiModelProperty("Users home railway station ID")
    private Long homeStationID;
    @ApiModelProperty("Users work railway station ID")
    private Long workStationID;
}
