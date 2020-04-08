package co.uk.suskins.commutestatus.common.repository;

import co.uk.suskins.commutestatus.common.models.entities.UserPreference;
import org.springframework.data.repository.CrudRepository;

public interface UserPreferenceRepository extends CrudRepository<UserPreference, Long> {
}
