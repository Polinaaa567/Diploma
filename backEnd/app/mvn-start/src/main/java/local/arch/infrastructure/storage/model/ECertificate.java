package local.arch.infrastructure.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "\"certificate\"")
public class ECertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certificate_id_seq")
    @SequenceGenerator(name = "certificate_id_seq", sequenceName = "certificate_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"id\"")
    private Integer certificateID;

    @OneToOne
    @JoinColumn(name = "\"fk_user_id\"", referencedColumnName = "\"id_user\"")
    private EUser fkUserID;

    @Column(name = "\"image_url\"")
    private String imageURL;
}
