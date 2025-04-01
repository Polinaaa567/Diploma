package local.arch.infrastructure.storage.model;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"users_points\"")
public class EPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_points_user_points_id_seq")
    @SequenceGenerator(name = "users_points_user_points_id_seq", sequenceName = "users_points_user_points_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"user_points_id\"")
    private Integer idUP ;

    @OneToOne
    @JoinColumn(name = "\"fk_user_id\"", referencedColumnName = "\"id_user\"")
    private EUser fkUserID;

    @Column(name = "\"points\"")
    private Integer points ;

    @Column(name = "\"date_change\"")
    private Timestamp dateChange;
}
