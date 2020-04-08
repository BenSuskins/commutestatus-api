package co.uk.suskins.commutestatus.common.repository;

import co.uk.suskins.commutestatus.common.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
