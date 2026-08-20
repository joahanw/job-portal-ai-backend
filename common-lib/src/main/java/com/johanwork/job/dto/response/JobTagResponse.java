package com.johanwork.job.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class JobTagResponse {

    private Long id;
    private String name;
    private String slug;
    private boolean active;

}
