package org.example.mypet.Service;

import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.User.SalaryDTO;
import org.example.mypet.Models.Salary;
import org.example.mypet.Models.User;
import org.example.mypet.Repository.SalaryRepository;
import org.example.mypet.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final UserRepository userRepository;

    public Salary createOrUpdateSalary(SalaryDTO dto) {
        User employee = userRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Salary salary = salaryRepository.findByEmployeeIdAndMonthAndYear(
                dto.getEmployeeId(), dto.getMonth(), dto.getYear()
        ).orElse(new Salary());

        salary.setEmployee(employee);
        salary.setMonth(dto.getMonth());
        salary.setYear(dto.getYear());
        salary.setWorkingHours(dto.getWorkingHours());
        salary.setRate(dto.getRate());
        salary.setTotalSalary(dto.getTotalSalary());
        salary.setPayDate(dto.getPayDate());

        return salaryRepository.save(salary);
    }

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public List<Salary> getSalariesByEmployee(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }

    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }

    public Salary getSalary(Long id) {
        return salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found"));
    }
}
