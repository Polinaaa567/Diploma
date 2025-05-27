package local.arch.infrastructure.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"levels\"")
public class ELevel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "levels_level_id_seq")
    @SequenceGenerator(name = "levels_level_id_seq", sequenceName = "levels_level_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"level_id\"")
    private Integer levelID ;

    @Column(name = "\"level_number\"")
    private Integer level;

    @Column(name = "\"max_number_points\"")
    private Integer maxPoints;
}
