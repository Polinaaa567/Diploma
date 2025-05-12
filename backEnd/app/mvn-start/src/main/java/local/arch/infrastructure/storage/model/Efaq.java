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
@Table(name = "\"faq\"")
public class Efaq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_faq_id_seq")
    @SequenceGenerator(name = "faq_faq_id_seq", sequenceName = "faq_faq_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"faq_id\"")
    private Integer faqID;

    @Column(name = "\"question\"")
    private String question;

    @Column(name = "\"answer\"")
    private String answer;
}
