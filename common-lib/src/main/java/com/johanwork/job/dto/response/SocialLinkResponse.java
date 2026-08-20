package com.johanwork.job.dto.response;

import com.johanwork.job.domain.SocialPlatform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SocialLinkResponse {
    private SocialPlatform platform;
    private String url;
}
