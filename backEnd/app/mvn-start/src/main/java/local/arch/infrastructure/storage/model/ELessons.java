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
@Table(name = "\"lessons\"")
public class ELessons {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessons_lesson_id_seq")
    @SequenceGenerator(name = "lessons_lesson_id_seq", sequenceName = "lessons_lesson_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "\"lesson_id\"")
    private Integer lessonID;

    @Column(name = "\"headline\"")
    private String headline;

    @Column(name = "\"link\"")
    private String link;

    @Column(name = "\"number_points\"")
    private Integer numberPoints;

    @Column(name = "\"date_creation\"")
    private Timestamp dateCreation;

    @Column(name = "\"description\"")
    private String description;
}
