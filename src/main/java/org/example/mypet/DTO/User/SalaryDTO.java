package org.example.mypet.DTO.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {
    private Long id;
    private Long employeeId;
    private Integer month;
    private Integer year;
    private Integer workingHours;
    @NotNull(message = "Rate cannot be null !")
    private Double rate;
    private Double totalSalary;
    private LocalDate payDate;
}
