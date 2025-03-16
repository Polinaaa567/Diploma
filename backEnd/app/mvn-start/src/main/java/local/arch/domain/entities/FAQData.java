package local.arch.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAQData {
    private Integer faqID;

    private String question;

    private String answer;
}
