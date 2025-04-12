package local.arch.infrastructure.storage.model;

import java.sql.Timestamp;

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
@Table(name = "\"news\"")
public class ENews {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_news_id_seq")
    @SequenceGenerator(name = "news_news_id_seq", sequenceName = "news_news_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"news_id\"")
    private Integer newsID;

    @Column(name = "\"headline_news\"")
    private String headlineNews;

    @Column(name = "\"description_news\"")
    private String descriptionNews;

    @Column(name = "\"date_creation\"")
    private Timestamp dateCreation;
}
