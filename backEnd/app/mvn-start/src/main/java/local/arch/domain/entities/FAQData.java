package local.arch.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAQData {
    private int faqID;

    private String question;

    private String answer;
}
