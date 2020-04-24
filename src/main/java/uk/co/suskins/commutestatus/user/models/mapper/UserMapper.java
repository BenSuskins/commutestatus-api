package uk.co.suskins.commutestatus.user.models.mapper;

import org.springframework.stereotype.Component;
import uk.co.suskins.commutestatus.common.models.entities.User;
import uk.co.suskins.commutestatus.common.models.entities.UserPreference;
import uk.co.suskins.commutestatus.user.models.api.UserReponse;

@Component
public class UserMapper {
    public UserReponse userToUserResponse(User user, UserPreference userPreference) {
        return UserReponse.builder()
                .email(user.getEmail())
                .workStation(userPreference.getWorkStation().getName())
                .homeStation(userPreference.getHomeStation().getName())
                .build();
    }
}
