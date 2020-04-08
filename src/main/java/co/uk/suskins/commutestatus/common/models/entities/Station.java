package co.uk.suskins.commutestatus.common.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station", schema = "commutestatus")
public class Station {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String crs;
    private String name;
    private Boolean active;
}
