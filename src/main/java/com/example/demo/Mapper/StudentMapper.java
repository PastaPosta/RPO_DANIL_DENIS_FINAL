package com.example.demo.Mapper;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.Model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target="idDto", source="id")
    @Mapping(target="fullNameDto", source="fullName")
    @Mapping(target="ageDto", source="age")
    @Mapping(target="groupIdDto", source="group.id")
    StudentDTO toDto(Student student);

    @Mapping(target="id", source="idDto")
    @Mapping(target="fullName", source="fullNameDto")
    @Mapping(target="age", source="ageDto")
    @Mapping(target="group", ignore=true)
    Student toEntity(StudentDTO studentDto);

    List<StudentDTO> toDtoList(List<Student> studentList);
}
