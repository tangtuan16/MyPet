package org.example.mypet.Controller;

import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.User.SalaryDTO;
import org.example.mypet.Models.Salary;
import org.example.mypet.Service.SalaryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Salary createSalary(@RequestBody SalaryDTO dto) {
        return salaryService.createOrUpdateSalary(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN') or #employeeId == principal.id")
    public List<Salary> getSalariesByEmployee(@PathVariable Long employeeId) {
        return salaryService.getSalariesByEmployee(employeeId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @salaryService.getSalary(#id).employee.id == principal.id")
    public Salary getSalary(@PathVariable Long id) {
        return salaryService.getSalary(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
    }
}
