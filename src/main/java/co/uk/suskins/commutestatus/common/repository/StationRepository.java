package co.uk.suskins.commutestatus.common.repository;

import co.uk.suskins.commutestatus.common.models.entities.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<Station, Long> {
}
