package org.example.mypet.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nhân viên nào
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    // Tháng + Năm
    private Integer month;
    private Integer year;

    // Số giờ làm trong tháng
    private Integer workingHours;

    // Lương cơ bản (theo hợp đồng hoặc rate)
    private Double rate;

    // Tổng lương (có thể = baseSalary hoặc tính từ giờ làm * rate)
    private Double totalSalary;

    private LocalDate payDate; // Ngày trả lương
}
