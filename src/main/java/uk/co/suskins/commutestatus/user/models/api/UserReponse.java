package uk.co.suskins.commutestatus.user.models.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReponse {
    String firstName;
    String lastName;
    String email;
    String workStation;
    String homeStation;
}
