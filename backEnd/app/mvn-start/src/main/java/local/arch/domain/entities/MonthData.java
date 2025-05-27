package local.arch.domain.entities;

import java.util.ArrayList;
import java.util.List;

import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthData {
    private String monthName;

    private Integer monthNumber;

    private List<Event> events = new ArrayList<>();

    private List<User> users = new ArrayList<>();
}
