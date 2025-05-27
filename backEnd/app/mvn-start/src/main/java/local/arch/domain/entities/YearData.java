package local.arch.domain.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YearData {
    private Integer year;

    private List<MonthData> month = new ArrayList<>();
}
