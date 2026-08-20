package com.johanwork.job.dto.response;

import com.johanwork.job.domain.LanguageProficiency;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class LanguageResponse {

    private Long id;
    private String languageName;
    private LanguageProficiency proficiency;
    private Integer displayOrder;

}
