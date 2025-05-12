package local.arch.domain.entities;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating {
    private User myPoints;

    private List<User> topPoints;
}
