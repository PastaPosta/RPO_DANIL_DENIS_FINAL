package com.example.demo.Service;
import com.example.demo.DTO.StudentDTO;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(long id);
    StudentDTO addStudent(StudentDTO studentDTO);
    boolean updateStudent(Long id, StudentDTO studentDTO);
    boolean deleteStudent(Long id);
}
