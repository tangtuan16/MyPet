package org.example.mypet.Controller;

import lombok.RequiredArgsConstructor;
import org.example.mypet.DTO.User.AttendanceDTO;
import org.example.mypet.Service.AttendanceService;
import org.example.mypet.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<ApiResponse<AttendanceDTO>> createAttendance(@RequestBody AttendanceDTO dto) {
        return ApiResponse.success(attendanceService.createAttendance(dto), HttpStatus.CREATED.value(), "Success!");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAllAttendances() {
        return ApiResponse.success(attendanceService.getAllAttendances(), "Success!");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AttendanceDTO>> getAttendance(@PathVariable Long id) {
        return ApiResponse.success(attendanceService.getAttendanceDTO(id), "Success");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AttendanceDTO>> updateAttendance(@PathVariable Long id, @RequestBody AttendanceDTO dto) {
        return ApiResponse.success(attendanceService.updateAttendance(id, dto),HttpStatus.OK.value(), "Success");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
    }

    @PostMapping("/generate-salary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> generateSalary(@RequestParam int month,
                                                              @RequestParam int year,
                                                              @RequestParam double defaultRate) {
        attendanceService.generateAllSalaries(month, year);
        return ApiResponse.success( "Salaries generated for " + month + "/" + year, "Success");
    }
}
