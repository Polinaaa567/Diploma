package local.arch.domain.entities.page;

import java.util.List;

import lombok.Data;

@Data
public class Rating {
    private User info;

    private Integer level;

    private Integer point;

    private Integer maxPoint;

    private Double percent;

    private List<String> certificates;
}
