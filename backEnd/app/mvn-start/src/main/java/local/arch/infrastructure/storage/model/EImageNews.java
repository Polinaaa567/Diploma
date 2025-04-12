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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"image_news\"")
public class EImageNews {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_news_photo_id_seq")
    @SequenceGenerator(name = "image_news_photo_id_seq", sequenceName = "image_news_photo_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"photo_id\"")
    private Integer imageID;

    @OneToOne
    @JoinColumn(name = "\"fk_news_id\"", referencedColumnName = "\"photo_id\"")
    private ENews news;

    @Column(name = "\"image_data\"")
    private Byte[] image;
}
