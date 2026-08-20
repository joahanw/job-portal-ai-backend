package com.johanwork.job.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Embeddable
public class JobLocation {
    private String address;
    private String city;
    private String country;
    private String state;
    private String zipCode;
}
