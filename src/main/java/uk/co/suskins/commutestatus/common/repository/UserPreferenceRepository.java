package uk.co.suskins.commutestatus.common.repository;

import org.springframework.data.repository.CrudRepository;
import uk.co.suskins.commutestatus.common.models.entities.UserPreference;

public interface UserPreferenceRepository extends CrudRepository<UserPreference, Long> {
}
