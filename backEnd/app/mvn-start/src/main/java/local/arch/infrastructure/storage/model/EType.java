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
@Table(name = "\"types\"")
public class EType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "types_id_type_seq")
    @SequenceGenerator(name = "types_id_type_seq", sequenceName = "types_id_type_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"id_type\"")
    private Integer id;

    @Column(name = "\"name\"")
    private String name;
}
