package com.johanwork.job.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Embeddable
public class SalaryRange {

    private BigDecimal minSalary;
    private BigDecimal maxSalary;

}
