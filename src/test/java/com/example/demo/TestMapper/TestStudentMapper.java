package com.example.demo.TestMapper;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.Mapper.StudentMapper;
import com.example.demo.Model.Group;
import com.example.demo.Model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestStudentMapper {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testToDto() {
        Student student = new Student(1L, "student_name1", 1, new Group(1L, "group_name1", new ArrayList<>(), new ArrayList<>()));

        StudentDTO studentDto = studentMapper.toDto(student);

        Assertions.assertNotNull(studentDto);

        Assertions.assertNotNull(studentDto.getIdDto());
        Assertions.assertNotNull(studentDto.getFullNameDto());
        Assertions.assertNotEquals(0, studentDto.getAgeDto());
        Assertions.assertNotNull(studentDto.getGroupIdDto());

        Assertions.assertEquals(student.getId(), studentDto.getIdDto());
        Assertions.assertEquals(student.getFullName(), studentDto.getFullNameDto());
        Assertions.assertEquals(student.getAge(), studentDto.getAgeDto());
        Assertions.assertEquals(student.getGroup().getId(), studentDto.getGroupIdDto());
    }

    @Test
    public void testToEntity() {
        StudentDTO studentDto = new StudentDTO(1L, "student_name1", 1, 1L);

        Student student = studentMapper.toEntity(studentDto);

        Assertions.assertNotNull(student);

        Assertions.assertNotNull(student.getId());
        Assertions.assertNotNull(student.getFullName());
        Assertions.assertNotEquals(0, student.getAge());

        Assertions.assertEquals(studentDto.getIdDto(), student.getId());
        Assertions.assertEquals(studentDto.getFullNameDto(), student.getFullName());
        Assertions.assertEquals(studentDto.getAgeDto(), student.getAge());
    }

    @Test
    public void testToDtoList() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "student_name1", 1, new Group(1L, "group_name1", new ArrayList<>(), new ArrayList<>())));
        studentList.add(new Student(2L, "student_name2", 2, new Group(2L, "group_name2", new ArrayList<>(), new ArrayList<>())));

        List<StudentDTO>  studentDtoList = studentMapper.toDtoList(studentList);

        Assertions.assertNotNull(studentDtoList);
        Assertions.assertNotEquals(0, studentDtoList.size());
        Assertions.assertEquals(studentList.size(), studentDtoList.size());

        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            StudentDTO studentDto = studentDtoList.get(i);

            Assertions.assertNotNull(studentDto);

            Assertions.assertNotNull(studentDto.getIdDto());
            Assertions.assertNotNull(studentDto.getFullNameDto());
            Assertions.assertNotEquals(0, studentDto.getAgeDto());
            Assertions.assertNotNull(studentDto.getGroupIdDto());

            Assertions.assertEquals(student.getId(), studentDto.getIdDto());
            Assertions.assertEquals(student.getFullName(), studentDto.getFullNameDto());
            Assertions.assertEquals(student.getAge(), studentDto.getAgeDto());
            Assertions.assertEquals(student.getGroup().getId(), studentDto.getGroupIdDto());
        }
    }
}
