package org.example.mypet.Utils.Mapper;

import org.example.mypet.DTO.User.AttendanceDTO;
import org.example.mypet.DTO.User.UserDTO;
import org.example.mypet.Models.Attendance;
import org.example.mypet.Models.User;

import java.util.stream.Collectors;

public class AttendanceMapper {

    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRoles(
                (user.getRoles() != null) ?
                        user.getRoles()
                        : null
        );
        return dto;
    }

    public static AttendanceDTO toAttendanceDTO(Attendance attendance) {
        if (attendance == null) return null;
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setEmployeeId(toUserDTO(attendance.getEmployee()).getId());
        dto.setWorkDate(attendance.getWorkDate());
        dto.setHoursWorked(attendance.getHoursWorked());
        dto.setIsAbsent(attendance.getIsAbsent());
        return dto;
    }
}
