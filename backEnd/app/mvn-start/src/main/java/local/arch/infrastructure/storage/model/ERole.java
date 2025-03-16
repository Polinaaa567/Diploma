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
@Table(name = "\"roles\"")
public class ERole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_role_seq")
    @SequenceGenerator(name = "roles_id_role_seq", sequenceName = "roles_id_role_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"id_role\"")
    private Integer idRole;

    @Column(name = "\"name_roles\"")
    private String nameRoles;
}
