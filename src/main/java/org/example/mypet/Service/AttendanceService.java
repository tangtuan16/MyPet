package org.example.mypet.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.User.AttendanceDTO;
import org.example.mypet.Models.Attendance;
import org.example.mypet.Models.Salary;
import org.example.mypet.Models.User;
import org.example.mypet.Repository.AttendanceRepository;
import org.example.mypet.Repository.SalaryRepository;
import org.example.mypet.Repository.UserRepository;
import org.example.mypet.Utils.Mapper.AttendanceMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final SalaryRepository salaryRepository;

    public AttendanceDTO createAttendance(AttendanceDTO dto) {
        // 1. Lấy employee
        User employee = userRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 2. Kiểm tra xem employee đã có attendance trong ngày chưa
        Attendance existing = attendanceRepository.findByEmployeeIdAndWorkDate(employee.getId(), dto.getWorkDate());

        int newHours = dto.getHoursWorked() != null ? dto.getHoursWorked() : 0;

        if (existing != null) {
            // 3. Cộng giờ làm nếu đã có attendance
            newHours += existing.getHoursWorked();
        }

        // 4. Kiểm tra giờ làm hợp lệ (không quá 24h/ngày)
        if (newHours > 24) {
            throw new RuntimeException("Total working hours for a day cannot exceed 24 hours");
        }

        // 5. Nếu đã có attendance, update
        if (existing != null) {
            existing.setHoursWorked(newHours);
            existing.setIsAbsent(dto.getIsAbsent() != null ? dto.getIsAbsent() : false);
            Attendance updated = attendanceRepository.save(existing);
            return AttendanceMapper.toAttendanceDTO(updated);
        }

        // 6. Nếu chưa có attendance, tạo mới
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setWorkDate(dto.getWorkDate());
        attendance.setHoursWorked(newHours);
        attendance.setIsAbsent(dto.getIsAbsent() != null ? dto.getIsAbsent() : false);

        Attendance saved = attendanceRepository.save(attendance);
        return AttendanceMapper.toAttendanceDTO(saved);
    }

    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll()
                .stream()
                .map(AttendanceMapper::toAttendanceDTO)
                .collect(Collectors.toList());
    }

    public AttendanceDTO getAttendanceDTO(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        return AttendanceMapper.toAttendanceDTO(attendance);
    }

    public Attendance getAttendance(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
    }


    public AttendanceDTO updateAttendance(Long id, AttendanceDTO dto) {
        Attendance attendance = getAttendance(id);
        attendance.setHoursWorked(dto.getHoursWorked());
        attendance.setIsAbsent(dto.getIsAbsent());
        attendance.setWorkDate(dto.getWorkDate());

        Attendance updated = attendanceRepository.save(attendance);
        return AttendanceMapper.toAttendanceDTO(updated);
    }


    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    @Transactional
    public void generateAllSalaries(int month, int year) {
        List<User> employees = userRepository.findAll();
        for (User e : employees) {
            generateMonthlySalary(e, month, year);
        }
    }

    @Transactional
    public void generateMonthlySalary(User employee, int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Attendance> attendances = attendanceRepository
                .findByEmployeeIdAndWorkDateBetween(employee.getId(), start, end);

        int totalHours = attendances.stream()
                .filter(a -> !Boolean.TRUE.equals(a.getIsAbsent()))
                .mapToInt(a -> a.getHoursWorked() != null ? a.getHoursWorked() : 0)
                .sum();

        Salary salary = salaryRepository.findByEmployeeIdAndMonthAndYear(employee.getId(), month, year)
                .orElse(new Salary());

        double rate = 25000;
        if (salary.getId() != null && salary.getRate() != null) {
            rate = salary.getRate();
        }

        salary.setEmployee(employee);
        salary.setMonth(month);
        salary.setYear(year);
        salary.setWorkingHours(totalHours);
        salary.setRate(rate);
        salary.setTotalSalary(totalHours * rate);
        salary.setPayDate(LocalDate.now());

        salaryRepository.save(salary);
    }
}
