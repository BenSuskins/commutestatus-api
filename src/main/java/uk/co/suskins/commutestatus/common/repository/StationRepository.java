package uk.co.suskins.commutestatus.common.repository;

import org.springframework.data.repository.CrudRepository;
import uk.co.suskins.commutestatus.common.models.entities.Station;

public interface StationRepository extends CrudRepository<Station, Long> {
}
