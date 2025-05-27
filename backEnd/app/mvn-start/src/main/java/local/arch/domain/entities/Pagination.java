package local.arch.domain.entities;

import java.util.List;

import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.Lesson;
import local.arch.domain.entities.page.News;
import lombok.Data;

@Data
public class Pagination {
    private Integer totalCount;

    private Integer countPage;

    private List<Event> events;

    private List<Lesson> lessons;

    private List<News> news;
}
