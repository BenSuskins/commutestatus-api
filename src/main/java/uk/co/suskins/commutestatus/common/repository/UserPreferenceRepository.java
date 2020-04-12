package uk.co.suskins.commutestatus.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.co.suskins.commutestatus.common.models.entities.UserPreference;

import java.util.Optional;

public interface UserPreferenceRepository extends CrudRepository<UserPreference, Long> {
    @Query("SELECT u FROM UserPreference u WHERE u.user.id = ?1")
    Optional<UserPreference> findByUserId(Long userId);
}
