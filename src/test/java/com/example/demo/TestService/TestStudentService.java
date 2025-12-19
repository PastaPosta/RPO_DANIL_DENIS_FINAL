package com.example.demo.TestService;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.Service.StudentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Random;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Commit
public class TestStudentService {
    @Autowired
    private StudentService studentService;

    Random random = new Random();

    @Test
    void testGetAllStudents() {
        List<StudentDTO> students =  studentService.getAllStudents();

        Assertions.assertNotNull(students);
        Assertions.assertNotEquals(0, students.size());

        for (StudentDTO student : students) {
            Assertions.assertNotNull(student);

            Assertions.assertNotNull(student.getIdDto());
            Assertions.assertNotNull(student.getFullNameDto());
            Assertions.assertNotEquals(0, student.getAgeDto());
        }
    }
    @Test
    void testGetStudentById() {
        int randomIndex =  random.nextInt(studentService.getAllStudents().size());
        Long id =  studentService.getAllStudents().get(randomIndex).getIdDto();

        StudentDTO student = studentService.getStudentById(id);
        Assertions.assertNotNull(student);

        Assertions.assertNotNull(student.getIdDto());
        Assertions.assertNotNull(student.getFullNameDto());
        Assertions.assertNotEquals(0, student.getAgeDto());

        student = studentService.getStudentById(-1L);
        Assertions.assertNull(student);
    }

    @Test
    void testAddStudent() {
        StudentDTO student = new StudentDTO();
        student.setFullNameDto("student_name1");
        student.setAgeDto(1);

        StudentDTO createdStudent = studentService.addStudent(student);
        StudentDTO checkStudent = studentService.getStudentById(createdStudent.getIdDto());

        Assertions.assertNotNull(checkStudent);
        Assertions.assertNotNull(createdStudent.getIdDto());
        Assertions.assertNotNull(createdStudent.getFullNameDto());
        Assertions.assertNotEquals(0, createdStudent.getAgeDto());

        Assertions.assertEquals(checkStudent.getIdDto(), createdStudent.getIdDto());
        Assertions.assertEquals(checkStudent.getFullNameDto(), createdStudent.getFullNameDto());
        Assertions.assertEquals(checkStudent.getAgeDto(), createdStudent.getAgeDto());

    }

    @Test
    void testUpdateStudent() {
        int randomIndex = random.nextInt(studentService.getAllStudents().size());
        Long id =  studentService.getAllStudents().get(randomIndex).getIdDto();

        StudentDTO student = new StudentDTO();
        student.setFullNameDto("changed_student_name");
        student.setAgeDto(random.nextInt(10));

        studentService.updateStudent(id, student);
        StudentDTO checkStudent = studentService.getStudentById(id);
        Assertions.assertNotNull(checkStudent);

        Assertions.assertNotNull(checkStudent.getIdDto());
        Assertions.assertNotNull(checkStudent.getFullNameDto());
        Assertions.assertNotEquals(0, checkStudent.getAgeDto());

        Assertions.assertEquals(checkStudent.getFullNameDto(), student.getFullNameDto());
        Assertions.assertEquals(checkStudent.getAgeDto(), student.getAgeDto());
    }

    @Test
    void testDeleteStudent() {
        int randomIndex =  random.nextInt(studentService.getAllStudents().size());
        Long id =  studentService.getAllStudents().get(randomIndex).getIdDto();

        Assertions.assertTrue(studentService.deleteStudent(id));

        StudentDTO student = studentService.getStudentById(id);
        Assertions.assertNull(student);
    }
}
